package com.myconnector.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DialogBox;

public class TrueDoClient implements EntryPoint {

	public void onModuleLoad() {
		//setUncaughtExceptionHandler();
		new ApplicationContext();
	}

	
	@SuppressWarnings("unused")
	private void setUncaughtExceptionHandler() {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable.getStackTrace();
					text += throwable.toString() + "\n";
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true);
				DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", " ");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});
	}

}
