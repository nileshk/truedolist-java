package com.myconnector.client.model;

import java.util.EventListener;

import com.myconnector.client.domain.UserDataClient;

public interface UserChangeListener extends EventListener {
	
	void onPasswordChange();
	
	void onEmailChanged(String newEmail);

	void onLoginCheck(UserDataClient userData);

}
