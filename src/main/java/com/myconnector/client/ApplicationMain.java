package com.myconnector.client;

import static com.myconnector.client.ClientUtil.createSeperator;
import static com.myconnector.client.ClientUtil.destroyWidget;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.composite.IItemListComposite;
import com.myconnector.client.composite.IListPanelComposite;
import com.myconnector.client.composite.settings.ISettingsComposite;
import com.myconnector.client.domain.TwoTodoClientLists;
import com.myconnector.client.domain.UserDataClient;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.listeners.RequestMoveListener;
import com.myconnector.client.model.DomainModel;
import com.myconnector.client.model.TodoListsChangeListener;
import com.myconnector.client.model.TodoListsInitListener;
import com.myconnector.client.model.UserChangeListener;
import com.myconnector.client.model.UserDomainModel;
import com.myconnector.client.service.TodoServiceGwtAsync;
import com.myconnector.client.service.UserServiceGwtAsync;

public class ApplicationMain implements WindowResizeListener, TodoListsChangeListener,
		ClickListener, TodoListsInitListener, Reloadable, UserChangeListener {

	private HorizontalSplitPanel splitPanel;
	private IListPanelComposite listPanelComposite;
	private List<ITodoList> todoLists = null;
	private Label userNameLabel;
	private Label preferencesLabel;
	private Label refreshLabel;
	private Label logoutLabel;	
	private TodoServiceGwtAsync todoService;
	private UserServiceGwtAsync userService;
	private UserDomainModel userDomainModel;
	private DomainModel domainModel;
	private TextConstants textConstants;	

	public void setDomainModel(DomainModel domainModel) {
		this.domainModel = domainModel;
		domainModel.addTodoListsChangeListener(this);
	}

	public void setTodoService(TodoServiceGwtAsync todoService) {
		this.todoService = todoService;
	}

	public void setUserService(UserServiceGwtAsync userService) {
		this.userService = userService;
	}
	
	public void setUserDomainModel(UserDomainModel userDomainModel) {
		this.userDomainModel = userDomainModel;
		this.userDomainModel.addUserChangeListener(this);
	}
	
	public void setTextConstants(TextConstants textConstants) {
		this.textConstants = textConstants;
	}

	/**
	 * This must be overridden
	 * 
	 * @param todoLists
	 * @return
	 */
	protected IListPanelComposite getListPanelComposite(List<ITodoList> todoLists) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This must be overridden
	 * 
	 * @param todoList
	 * @param todoListsIn
	 * @return
	 */
	protected IItemListComposite getItemListComposite(ITodoList todoList,
			List<ITodoList> todoListsIn) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This must be overridden
	 * @return
	 */
	protected ISettingsComposite getSettingsComposite() {
		throw new UnsupportedOperationException();
	}

	public void init() {
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(3);
		splitPanel = new HorizontalSplitPanel();
		splitPanel.setSplitPosition("15%");
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.setWidget(splitPanel);
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setSpacing(4);
		userNameLabel = new Label();
		preferencesLabel = new Label(textConstants.preferences());
		preferencesLabel.addStyleName(ClientConstants.CSS_CLICKABLE_LABEL);
		preferencesLabel.addClickListener(this);
		refreshLabel = new Label(textConstants.refresh());
		refreshLabel.addStyleName(ClientConstants.CSS_CLICKABLE_LABEL);
		refreshLabel.addClickListener(this);
		logoutLabel = new Label(textConstants.logout());
		logoutLabel.addStyleName(ClientConstants.CSS_CLICKABLE_LABEL);
		logoutLabel.addClickListener(this);
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		headerPanel.add(userNameLabel);
		headerPanel.add(createSeperator());
		headerPanel.add(preferencesLabel);
		headerPanel.add(createSeperator());
		headerPanel.add(refreshLabel);
		headerPanel.add(createSeperator());
		headerPanel.add(logoutLabel);
		vPanel.add(headerPanel);
		vPanel.add(decoratorPanel);

		domainModel.addTodoListsInitListener(this);

		RootPanel.get().add(vPanel);

		// Setup for Window resizing
		Window.addWindowResizeListener(this);

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onWindowResized(Window.getClientWidth(), Window.getClientHeight());
			}
		});

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				callServicesAndSetup();
			}
		});
		onWindowResized(Window.getClientWidth(), Window.getClientHeight());

	}

	public void reload() {
		domainModel.reload();
	}

	public void onWindowResized(int width, int height) {
		splitPanel.setPixelSize(width - splitPanel.getAbsoluteLeft() - 8, height
				- splitPanel.getAbsoluteTop() - 8);
	}

	private void callServicesAndSetup() {
		String cookieValue = Cookies.getCookie(ClientConstants.USER_COOKIE_NAME);
		ClientUtil.showBlockingPanel();
		userDomainModel.loginCheckWithCookie(cookieValue);
		setupLoginCheckTimer(cookieValue);
	}

	public void onTodoListsInit(List<ITodoList> todoLists) {
		ApplicationMain.this.todoLists = todoLists;
		destroyWidget(splitPanel.getLeftWidget());
		listPanelComposite = getListPanelComposite(todoLists);
		splitPanel.setLeftWidget(listPanelComposite.asWidget());
		destroyWidget(splitPanel.getRightWidget());
		splitPanel.remove(splitPanel.getRightWidget());
	}

	public void onClick(Widget sender) {
		if (sender == logoutLabel) {
			Window.Location.assign(ClientConstants.LOGOUT_PAGE_URL);
		} else if (sender == refreshLabel) {
			reload();
		} else if (sender == preferencesLabel) {
			openPreferencesPage();
		}
	}

	private void setupLoginCheckTimer(final String cookieValue) {
		Timer loginCheckTimer = new Timer() {

			@Override
			public void run() {
				userService.checkIfLoggedIn(cookieValue, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						ExceptionHandler.handle(caught);
					}

					public void onSuccess(Boolean result) {
						if (result == null || !result.booleanValue()) {
							Window.Location.assign(ClientConstants.LOGIN_PAGE_URL);
						}
					}
				});
			}
		};
		// TODO get session timeout from the server instead? - JavaScript variable?
		loginCheckTimer.scheduleRepeating(ClientConstants.SESSION_CHECK_INTERVAL);
	}

	private void openPreferencesPage() {
		Widget currentRightWidget = splitPanel.getRightWidget();
		destroyWidget(currentRightWidget);
		splitPanel.setRightWidget(getSettingsComposite().asWidget());
	}
	
	private void handleMoveDown(TwoTodoClientLists result) {
		if (result != null) {
			ITodoList todoList1 = result.getTodoList1();
			ITodoList todoList2 = result.getTodoList2();
			for (int i = 0; i < todoLists.size(); i++) {
				ITodoList todoList = todoLists.get(i);
				if (todoList.getId().equals(todoList1.getId())) {
					if (i == 0) {
						return;
					}
					ITodoList toMove = todoLists.remove(i);
					todoLists.add(i - 1, toMove);
					toMove.setPosition(todoList1.getPosition());
					ITodoList other = todoLists.get(i);
					if (other.getId().equals(todoList2.getId())) {
						other.setPosition(todoList2.getPosition());
					} else {
						GWT.log("TodoLists out of sync?", null);
					}
					listPanelComposite.refresh();
				}

			}
		}
	}

	private void handleMoveUp(TwoTodoClientLists result) {
		if (result != null) {
			ITodoList todoList1 = result.getTodoList1();
			ITodoList todoList2 = result.getTodoList2();
			for (int i = 0; i < todoLists.size(); i++) {
				ITodoList todoList = todoLists.get(i);
				if (todoList.getId().equals(todoList2.getId())) {
					if (i == 0) {
						return;
					}
					ITodoList toMove = todoLists.remove(i);
					todoLists.add(i - 1, toMove);
					toMove.setPosition(todoList1.getPosition());
					ITodoList other = todoLists.get(i);
					if (other.getId().equals(todoList1.getId())) {
						other.setPosition(todoList1.getPosition());
					} else {
						GWT.log("TodoLists out of sync?", null);
					}
					listPanelComposite.refresh();
				}

			}
		}
	}

	public void onTodoListSelected(Object originator, ITodoList todoList) {
		Widget currentRightWidget = splitPanel.getRightWidget();
		destroyWidget(currentRightWidget);
		// if(currentRightWidget != null && currentRightWidget instanceof IItemListComposite) {
		// IItemListComposite currentItemListComposite = (IItemListComposite) currentRightWidget;
		//			
		// }
		// TODO cache itemListComposite objects?
		final IItemListComposite itemListComposite = getItemListComposite(todoList, todoLists);
		itemListComposite.addRequestMoveListener(new RequestMoveListener() {
			// TODO switch to using Domain Object for these
			public void onRequestMoveDown(Widget sender) {
				if (sender instanceof IItemListComposite) {
					IItemListComposite itemListCompositeSender = (IItemListComposite) sender;
					ClientUtil.showBlockingPanel();
					todoService.moveTodoListDown(itemListCompositeSender.getTodoListId(),
							new AsyncCallback<TwoTodoClientLists>() {
								public void onFailure(Throwable caught) {
									ExceptionHandler.handle(caught);
								}

								public void onSuccess(TwoTodoClientLists result) {
									try {
										handleMoveDown(result);
									} finally {
										ClientUtil.hideBlockingPanel();
									}
								}

							});
				}

			}

			public void onRequestMoveUp(Widget sender) {
				if (sender instanceof IItemListComposite) {
					IItemListComposite itemListCompositeSender = (IItemListComposite) sender;
					ClientUtil.showBlockingPanel();
					todoService.moveTodoListUp(itemListCompositeSender.getTodoListId(),
							new AsyncCallback<TwoTodoClientLists>() {
								public void onFailure(Throwable caught) {
									ExceptionHandler.handle(caught);
								}

								public void onSuccess(TwoTodoClientLists result) {
									try {
										handleMoveUp(result);
									} finally {
										ClientUtil.hideBlockingPanel();
									}
								}

							});
				}
			}
		});

		splitPanel.setRightWidget(itemListComposite.asWidget());
	}

	public void onTodoListDeleted(Object originator, ITodoList todoList) {
		if (originator instanceof IItemListComposite) {
			IItemListComposite itemListCompositeSender = (IItemListComposite) originator;
			listPanelComposite.refresh();
			itemListCompositeSender.removeFromParent();
		}
	}

	public void onTodoItemDeleted(Object originator, ITodoItem todoItem) {
	}

	public void onTodoListAdded(Object originator, ITodoList todoList) {
	}

	public void onTodoItemModified(Object originator, ITodoItem todoItem) {
	}

	public void onTodoItemMoveDown(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {
	}

	public void onTodoItemMoveUp(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {
	}

	public void onTodoItemAdded(Object originator, ITodoList todoList, ITodoItem todoItem) {
	}

	public void onTodoListItemsFetched(Object originator, ITodoList todoList) {
	}

	public void onMultipleTodoItemsDelete(Object sender, ITodoList todoList, List<Long> itemIds) {
	}

	public void onMultipleTodoItemsMoved(Object sender, ITodoList todoList,
			Long destinationTodoListId, List<Long> itemIds) {
	}

	public void onTodoListTitleUpdated(Object sender, ITodoList todoList, String title) {
	}

	public void onTodoItemMoved(ITodoList sourceTodoList, ITodoList destinationTodoList,
			ITodoItem todoItemToMove) {
	}

	public void onTodoItemRepositioned(ITodoItem todoItem, ITodoItem beforeTodoItem,
			ITodoList todoList) {
	}

	public void onTodoListRepositioned(ITodoList todoList, ITodoList beforeTodoList) {
	}

	public void onEmailChanged(String newEmail) {	
	}

	public void onLoginCheck(UserDataClient userData) {
		try {
			if (userData == null) {
				Window.Location.assign(ClientConstants.LOGIN_PAGE_URL);
			} else {
				userNameLabel.setText(userData.getUsername());
				domainModel.init();
			}
		} finally {
			ClientUtil.hideBlockingPanel();
		}
	}

	public void onPasswordChange() {
	}

}
