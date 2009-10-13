package com.myconnector.client;

import com.google.gwt.i18n.client.Constants;

public interface TextConstants extends Constants {

	String preferences();
	String refresh();
	String logout();
	String loadingIndicator();
	String plusNewList();
	String ok();
	String cancel();
	String yes();
	String no();
	String moveUp();
	String moveDown();
	String delete();
	String importText();
	String save();
	String addItem();
	String deleteCheckedItems();
	String moveToList();
	String moveCheckedItemsTo();
	String deleteConfirmation();
	String deleteCheckedConfirmation();
	String highlight();
	String unhighlight();
	
	// Settings
	String accountSettingsTab();
	String changePasswordHeader();
	String currentPassword();
	String newPassword();
	String newPasswordConfirmation();
	String changePasswordButton();
	String passwordChangeSuccessful();
	String currentEmail();
	String changeEmailHeader();
	String changeEmailButton();
	String newEmail();
	String emailChangeSuccessful();
	
	// Settings errors
	String currentPasswordEmpty();
	String newPasswordEmpty();
	String newPasswordConfirmationEmpty();
	String newPasswordConfirmationDoesNotMatch();
	String currentPasswordIncorrect();
	String newEmailEmpty();
	String newEmailInvalid();	
	
	

}
