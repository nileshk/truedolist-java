package com.myconnector.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myconnector.client.domain.UserDataClient;
import com.myconnector.client.service.UserServiceGwtAsync;

public class UserDomainModelImpl extends BaseDomainModel implements UserDomainModel {

	private UserServiceGwtAsync userService;
	private List<UserChangeListener> userChangeListeners = new ArrayList<UserChangeListener>();
	private UserDataClient userData = null;

	public void setUserService(UserServiceGwtAsync userService) {
		this.userService = userService;
	}

	public void addUserChangeListener(UserChangeListener listener) {
		userChangeListeners.add(listener);
	}

	public void removeUserChangeListener(UserChangeListener listener) {
		userChangeListeners.remove(listener);
	}
	
	public UserDataClient getUserData() {
		return userData;
	}

	public void loginCheckWithCookie(String cookieValue) {
		begin();
		userService.loginCheckWithCookie(cookieValue, new AsyncCallback<UserDataClient>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}
			
			public void onSuccess(final UserDataClient result) {
				try {
					userData = result;
					executeOnListeners(new ListenerCommand() {
						public void execute(UserChangeListener listener) {
							listener.onLoginCheck(result);
						}
					});
					
				} finally {
					end();
				}
			}
		});
	}

	public void changeEmail(final String newEmail) {
		begin();
		userService.changeEmail(newEmail, new AsyncCallback<Object>() {

			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					executeOnListeners(new ListenerCommand() {
						public void execute(UserChangeListener listener) {
							listener.onEmailChanged(newEmail);
						}
					});
				} finally {
					end();
				}
			}

		});
	}

	public void changePassword(final String currentPassword, final String newPassword) {
		begin();
		userService.changePassword(currentPassword, newPassword, new AsyncCallback<Object>() {

			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					executeOnListeners(new ListenerCommand() {
						public void execute(UserChangeListener listener) {
							listener.onPasswordChange();
						}
					});
				} finally {
					end();
				}
			}

		});
	}

	/**
	 * Executes a command on all change listeners
	 * 
	 * @param command
	 */
	private void executeOnListeners(final ListenerCommand command) {
		for (final UserChangeListener listener : userChangeListeners) {
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					command.execute(listener);
				}
			});
		}
	}

	/**
	 * Command to execute on a change listener
	 */
	private interface ListenerCommand {
		void execute(UserChangeListener listener);
	}

}
