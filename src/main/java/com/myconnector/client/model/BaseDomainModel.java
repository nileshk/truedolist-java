package com.myconnector.client.model;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.myconnector.client.ClientUtil;
import com.myconnector.client.ExceptionHandler;

public class BaseDomainModel {

	/**
	 * Call this right before an RPC call.
	 */
	protected void begin() {
		GWT.log("Beginning transaction", null);
		ClientUtil.showBlockingPanel();
	}

	/**
	 * Call this once an RPC call is finished and all listeners have been notified. This is
	 * generally in the onSuccess method of the callback. Wrap the contents of onSuccess in a try
	 * and call end() in the finally. Avoid nesting begin/end calls as it may prematurely end. We
	 * may have to do a stack-based implementation if that becomes a problem.
	 */
	protected void end() {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				GWT.log("Ending transaction", null);
				ClientUtil.hideBlockingPanel();
			}
		});
	}	

	protected void handle(Throwable caught) {
		ExceptionHandler.handle(caught);
	}
}
