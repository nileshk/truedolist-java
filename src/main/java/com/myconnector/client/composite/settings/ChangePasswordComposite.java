package com.myconnector.client.composite.settings;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.ClientConstants;

public class ChangePasswordComposite extends BaseUserSettingsComposite implements
		IChangePasswordComposite, ClickListener {

	private TextBox currentPasswordTextBox;
	private TextBox newPasswordTextBox;
	private TextBox newPasswordConfirmationTextBox;
	private Button changePasswordButton;

	public void init() {
		VerticalPanel vPanel = new VerticalPanel();
		Label changePasswordHeaderLabel = new Label(textConstants.changePasswordHeader());
		changePasswordHeaderLabel.addStyleName(ClientConstants.CSS_SETTINGS_HEADER_LABEL);
		vPanel.add(changePasswordHeaderLabel);

		flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new Label(textConstants.currentPassword()));
		flexTable.setWidget(1, 0, new Label(textConstants.newPassword()));
		flexTable.setWidget(2, 0, new Label(textConstants.newPasswordConfirmation()));

		currentPasswordTextBox = new PasswordTextBox();
		newPasswordTextBox = new PasswordTextBox();
		newPasswordConfirmationTextBox = new PasswordTextBox();
		changePasswordButton = new Button(textConstants.changePasswordButton());
		changePasswordButton.addClickListener(this);

		flexTable.setWidget(0, 1, currentPasswordTextBox);
		flexTable.setWidget(1, 1, newPasswordTextBox);
		flexTable.setWidget(2, 1, newPasswordConfirmationTextBox);
		flexTable.setWidget(3, 1, changePasswordButton);

		vPanel.add(flexTable);
		initWidget(vPanel);
	}

	public void onClick(Widget sender) {
		if (sender == changePasswordButton) {
			changePassword();
		}
	}

	private void changePassword() {
		clearMessages();
		String currentPassword = currentPasswordTextBox.getText();
		String newPassword = newPasswordTextBox.getText();
		String newPasswordConfirmation = newPasswordConfirmationTextBox.getText();
		errorCount = 0;
		if (currentPassword == null || currentPassword.length() == 0) {
			addValidationError(textConstants.currentPasswordEmpty(), 0);
		}
		if (newPassword == null || newPassword.length() == 0) {
			addValidationError(textConstants.newPasswordEmpty(), 1);
		}
		if (newPasswordConfirmation == null || newPasswordConfirmation.length() == 0) {
			addValidationError(textConstants.newPasswordConfirmationEmpty(), 2);
		} else if (!newPasswordConfirmation.equals(newPassword)) {
			addValidationError(textConstants.newPasswordConfirmationDoesNotMatch(), 2);
		}
		if (errorCount == 0) {
			userDomainModel.changePassword(currentPassword, newPassword);
		}
	}

	public void onPasswordChange() {
		clearMessages();
		Label label = new Label(textConstants.passwordChangeSuccessful());
		label.addStyleName(ClientConstants.CSS_SETTING_CHANGE_SUCCESS_MESSAGE);
		messages.add(label);
		flexTable.setWidget(4, 1, label);
		currentPasswordTextBox.setText("");
		newPasswordTextBox.setText("");
		newPasswordConfirmationTextBox.setText("");
	}

}
