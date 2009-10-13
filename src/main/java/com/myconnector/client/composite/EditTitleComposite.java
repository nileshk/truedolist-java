package com.myconnector.client.composite;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.TextConstants;
import com.myconnector.client.listeners.OkCancelListener;
import com.myconnector.client.listeners.RequestDeleteListener;
import com.myconnector.client.listeners.RequestImportTextListener;
import com.myconnector.client.listeners.RequestMoveListener;

public class EditTitleComposite extends Composite implements ClickListener, KeyboardListener {

	private TextBox textBox;
	private Button okButton;
	private Button cancelButton;
	private Button deleteButton;
	private Button moveUpButton;
	private Button moveDownButton;
	private Button unhideImportTextButton;
	private List<OkCancelListener> okCancelListeners = new ArrayList<OkCancelListener>();
	private List<RequestDeleteListener> requestDeleteListeners = new ArrayList<RequestDeleteListener>();
	private List<RequestMoveListener> requestMoveListeners = new ArrayList<RequestMoveListener>();
	private List<RequestImportTextListener> requestImportTextListeners = new ArrayList<RequestImportTextListener>();

	public EditTitleComposite() {
		TextConstants textConstants = GWT.create(TextConstants.class);
		HorizontalPanel mainPanel = new HorizontalPanel();
		textBox = new TextBox();
		textBox.addKeyboardListener(this);

		okButton = new Button(textConstants.ok());
		cancelButton = new Button(textConstants.cancel());
		deleteButton = new Button(textConstants.delete());
		moveUpButton = new Button(textConstants.moveUp());
		moveDownButton = new Button(textConstants.moveDown());
		unhideImportTextButton = new Button(textConstants.importText());

		mainPanel.add(textBox);
		mainPanel.add(okButton);
		mainPanel.add(cancelButton);
		mainPanel.add(moveUpButton);
		mainPanel.add(moveDownButton);
		mainPanel.add(deleteButton);
		mainPanel.add(unhideImportTextButton);

		okButton.addClickListener(this);
		cancelButton.addClickListener(this);
		deleteButton.addClickListener(this);
		moveUpButton.addClickListener(this);
		moveDownButton.addClickListener(this);
		unhideImportTextButton.addClickListener(this);

		disableOkIfTextBoxIsEmpty();
		
		initWidget(mainPanel);
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				focus();
			}
		});
	}

	public void addOkCancelListener(OkCancelListener listener) {
		okCancelListeners.add(listener);
	}

	public void addRequestDeleteListener(RequestDeleteListener listener) {
		requestDeleteListeners.add(listener);
	}

	public void addRequestMoveListener(RequestMoveListener listener) {
		requestMoveListeners.add(listener);
	}

	public void addRequestImportTextListeners(RequestImportTextListener listener) {
		requestImportTextListeners.add(listener);
	}

	public void onClick(Widget sender) {
		if (sender == okButton) {
			doOk();
		} else if (sender == cancelButton) {
			doCancel();
		} else if (sender == deleteButton) {
			doDelete();
		} else if (sender == moveUpButton) {
			doMoveUp();
		} else if (sender == moveDownButton) {
			doMoveDown();
		} else if (sender == unhideImportTextButton) {
			doImportText();
		}
	}

	private void doOk() {
		for (OkCancelListener listener : okCancelListeners) {
			listener.onOk(this);
		}
	}

	private void doCancel() {
		for (OkCancelListener listener : okCancelListeners) {
			listener.onCancel(this);
		}
	}

	private void doDelete() {
		for (RequestDeleteListener listener : requestDeleteListeners) {
			listener.onRequestDelete(this);
		}
	}

	private void doMoveUp() {
		for (RequestMoveListener listener : requestMoveListeners) {
			listener.onRequestMoveUp(this);
		}
	}

	private void doMoveDown() {
		for (RequestMoveListener listener : requestMoveListeners) {
			listener.onRequestMoveDown(this);
		}
	}

	private void doImportText() {
		for (RequestImportTextListener listener : requestImportTextListeners) {
			listener.onRequestImportText(this);
		}
	}

	public String getText() {
		return textBox.getText();
	}

	public void setText(String text) {
		textBox.setText(text);
		disableOkIfTextBoxIsEmpty();
	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		if (sender == textBox) {
			if (keyCode == KEY_ESCAPE) {
				doCancel();
			}
			if (disableOkIfTextBoxIsEmpty()) {
				if (keyCode == KEY_ENTER) {
					doOk();
				}
			}
		}
	}

	public void focus() {
		textBox.setFocus(true);
	}

	private boolean disableOkIfTextBoxIsEmpty() {
		String text = textBox.getText();
		if (text == null || text.length() == 0) {
			okButton.setEnabled(false);
			return false;
		} else {
			okButton.setEnabled(true);
			return true;
		}
	}

}
