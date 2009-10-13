package com.myconnector.client.model;

import com.myconnector.client.domain.UserDataClient;

public interface UserDomainModel {

	void addUserChangeListener(UserChangeListener listener);

	void removeUserChangeListener(UserChangeListener listener);

	UserDataClient getUserData();
	
	void loginCheckWithCookie(String cookieValue);
	
    void changePassword(String currentPassword, String newPassword);
    
    void changeEmail(String newEmail);
	
}
