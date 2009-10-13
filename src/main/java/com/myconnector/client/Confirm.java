package com.myconnector.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.listeners.ConfirmListener;

public class Confirm extends Composite implements ClickListener {

	private String message = "Are you sure?";
	private Button yesButton;
	private Button noButton;
	private List<ConfirmListener> confirmListeners = new ArrayList<ConfirmListener>();
	private TextConstants textConstants;
	private String yesText = null;
	private String noText = null;

	public Confirm() {
		init();
	}

	public Confirm(String message) {
		this.message = message;
		init();
	}

	public Confirm(String message, String yesText, String noText) {
		this.message = message;
		this.yesText = yesText;
		this.noText = noText;
		init();
	}

	public void init() {
		textConstants = GWT.create(TextConstants.class);
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(new Label(message));
		HorizontalPanel buttonPanel = new HorizontalPanel();
		if (yesText == null) {
			yesText = textConstants.yes();
		}
		if (noText == null) {
			noText = textConstants.no();
		}
		yesButton = new Button(yesText);
		noButton = new Button(noText);
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);
		mainPanel.add(buttonPanel);
		yesButton.addClickListener(this);
		noButton.addClickListener(this);

		initWidget(mainPanel);
	}

	public void addConfirmListener(ConfirmListener listener) {
		confirmListeners.add(listener);
	}

	public void onClick(Widget sender) {
		if (sender == yesButton) {
			doYes();
		} else {
			doNo();
		}
	}

	private void doNo() {
		for (ConfirmListener listener : confirmListeners) {
			listener.onNo(this);
		}
	}

	private void doYes() {
		for (ConfirmListener listener : confirmListeners) {
			listener.onYes(this);
		}
	}

	public static void confirm(String message, ConfirmListener confirmListener) {
		confirm(message, confirmListener, null, null);
	}

	public static void confirm(String message, ConfirmListener confirmListener, String yesText,
			String noText) {
		Confirm confirm = new Confirm(message, yesText, noText);
		confirm.addConfirmListener(confirmListener);
		final PopupPanel popupPanel = PopupUtil.popup(confirm);
		confirm.addConfirmListener(new ConfirmListener() {
			public void clearPanel() {
				PopupUtil.close(popupPanel);
			}

			public void onNo(Widget sender) {
				clearPanel();
			}

			public void onYes(Widget sender) {
				clearPanel();
			}
		});
	}

}
