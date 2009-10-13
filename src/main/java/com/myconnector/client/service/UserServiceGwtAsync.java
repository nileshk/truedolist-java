package com.myconnector.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myconnector.client.domain.UserDataClient;

public interface UserServiceGwtAsync {

	void loginCheckWithCookie(String cookieValue, AsyncCallback<UserDataClient> callback);

	void checkIfLoggedIn(String cookieValue, AsyncCallback<Boolean> callback);

	void changePassword(String currentPassword, String newPassword, AsyncCallback<Object> callback);
	
	void changeEmail(String newEmail, AsyncCallback<Object> callback);
}
