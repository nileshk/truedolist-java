package com.myconnector.client.composite.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.AsWidget;
import com.myconnector.client.ClientConstants;
import com.myconnector.client.TextConstants;
import com.myconnector.client.domain.UserDataClient;
import com.myconnector.client.model.UserChangeListener;
import com.myconnector.client.model.UserDomainModel;

public class BaseUserSettingsComposite extends Composite implements AsWidget, UserChangeListener {

	protected static final int VALIDATION_ERROR_COLUMN = 2;
	protected TextConstants textConstants;
	protected UserDomainModel userDomainModel;
	protected FlexTable flexTable = new FlexTable();
	protected List<Widget> messages = new ArrayList<Widget>();
	protected int errorCount;

	public void setTextConstants(TextConstants textConstants) {
		this.textConstants = textConstants;
	}

	public void setUserDomainModel(UserDomainModel userDomainModel) {
		this.userDomainModel = userDomainModel;
		this.userDomainModel.addUserChangeListener(this);
	}

	public Widget asWidget() {
		return this;
	}	

	protected void addValidationError(String text, int row) {
		Label label = new Label(text);
		label.addStyleName(ClientConstants.CSS_VALIDATION_ERROR_LABEL);
		messages.add(label);
		flexTable.setWidget(row, VALIDATION_ERROR_COLUMN, label);
		errorCount++;
	}
	
	protected void clearMessages() {
		for (Widget widget : messages) {
			flexTable.remove(widget);
		}
		messages.clear();
	}	
	
	public void onEmailChanged(String newEmail) {
	}

	public void onLoginCheck(UserDataClient userData) {
	}

	public void onPasswordChange() {
	}

}
