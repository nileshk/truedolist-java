package com.myconnector.client.composite.settings;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.TextConstants;

/**
 * User settings screen
 * 
 */
public class SettingsComposite extends Composite implements ISettingsComposite {

	protected TextConstants textConstants;
	private IChangeEmailComposite changeEmailComposite;
	private IChangePasswordComposite changePasswordComposite;

	public void setTextConstants(TextConstants textConstants) {
		this.textConstants = textConstants;
	}

	public void setChangeEmailComposite(IChangeEmailComposite changeEmailComposite) {
		this.changeEmailComposite = changeEmailComposite;
	}

	public void setChangePasswordComposite(IChangePasswordComposite changePasswordComposite) {
		this.changePasswordComposite = changePasswordComposite;
	}

	public void init() {
		TabPanel tabPanel = new DecoratedTabPanel();
		VerticalPanel vPanel = new VerticalPanel();

		vPanel.add(changePasswordComposite.asWidget());
		vPanel.add(changeEmailComposite.asWidget());

		tabPanel.add(vPanel, textConstants.accountSettingsTab());
		tabPanel.selectTab(0);
		initWidget(tabPanel);
	}

	public Widget asWidget() {
		return this;
	}

}
