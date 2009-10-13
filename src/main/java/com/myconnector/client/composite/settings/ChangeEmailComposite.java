package com.myconnector.client.composite.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.ClientConstants;
import com.myconnector.client.domain.UserDataClient;

public class ChangeEmailComposite extends BaseUserSettingsComposite implements
		IChangeEmailComposite, ClickListener {

	private TextBox emailAddressTextBox;
	private Label currentEmailAddress;
	private Button changeEmailButton;	
	private List<Widget> messages = new ArrayList<Widget>();	
	
	public void init() {
		VerticalPanel vPanel = new VerticalPanel();

		Label changeEmailHeaderLabel = new Label(textConstants.changeEmailHeader());
		changeEmailHeaderLabel.addStyleName(ClientConstants.CSS_SETTINGS_HEADER_LABEL);
		vPanel.add(changeEmailHeaderLabel);

		emailAddressTextBox = new TextBox();
		changeEmailButton = new Button(textConstants.changeEmailButton());
		changeEmailButton.addClickListener(this);
		currentEmailAddress = new Label(userDomainModel.getUserData().getEmail());

		flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new Label(textConstants.currentEmail()));
		flexTable.setWidget(1, 0, new Label(textConstants.newEmail()));

		flexTable.setWidget(0, 1, currentEmailAddress);
		flexTable.setWidget(1, 1, emailAddressTextBox);
		flexTable.setWidget(2, 1, changeEmailButton);
		vPanel.add(flexTable);
		initWidget(vPanel);
	}

	public void onClick(Widget sender) {
		if (sender == changeEmailButton) {
			changeEmail();
		}
	}	
	
	private void changeEmail() {
		clearMessages();
		String newEmail = emailAddressTextBox.getText();
		errorCount = 0;
		if (newEmail == null || newEmail.length() == 0) {
			addValidationError(textConstants.newEmailEmpty(), 1);
		} else if (!newEmail.contains("@") || !newEmail.contains(".")) {
			// TODO better regex for email
			addValidationError(textConstants.newEmailInvalid(), 1);
		}
		if (errorCount == 0) {
			userDomainModel.changeEmail(newEmail);
		}
	}

	private void setEmail(String emailAddress) {
		currentEmailAddress.setText(emailAddress);
	}

	public void onEmailChanged(String newEmail) {
		setEmail(newEmail);
		clearMessages();
		Label label = new Label(textConstants.emailChangeSuccessful());
		label.addStyleName(ClientConstants.CSS_SETTING_CHANGE_SUCCESS_MESSAGE);
		messages.add(label);
		flexTable.setWidget(3, 1, label);
		emailAddressTextBox.setText("");
	}

	public void onLoginCheck(UserDataClient result) {
		setEmail(result.getEmail());
	}
	
}
