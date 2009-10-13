package com.myconnector.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.composite.IDestroyable;

public class ClientUtil {

	static private PopupPanel blockingPanel;

	static {
		TextConstants textConstants = GWT.create(TextConstants.class);
		blockingPanel = new PopupPanel(false, true);
		blockingPanel.add(new Label(textConstants.loadingIndicator()));
	}

	public static void refreshWidget(Widget widget) {
		if (widget != null && widget instanceof Refreshable) {
			((Refreshable) widget).refresh();
		}
	}

	public static void destroyWidget(Widget widget) {
		if (widget != null && widget instanceof IDestroyable) {
			((IDestroyable) widget).destroy();
		}
	}

	public static Widget createSeperator() {
		return new HTML("&nbsp;|&nbsp;");
	}

	public static void showBlockingPanel() {
		blockingPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) - 100;
				int top = 0;
				blockingPanel.setPopupPosition(left, top);
			}
		});

	}

	public static void hideBlockingPanel() {
		blockingPanel.hide();
	}

}
