package com.myconnector.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ExceptionHandler {

    public static void handle(Throwable caught) {
    	ClientUtil.hideBlockingPanel();
        try {
            throw caught;
        } catch (ClientMessageException e) {
            handleClientMessageException(e);
        } catch (Throwable e) {
            GWT.log("Unexpected exception", e);
            List<String> messages = new ArrayList<String>();
            messages.add("Unexpected error:");
            messages.add(e.getMessage());
            MessageBox.show(messages);
        }

    }

    private static void handleClientMessageException(ClientMessageException e) {
        VerticalPanel mainPanel = new VerticalPanel();
        if(e.getCode() != null && e.getCode().equals(ClientConstants.USER_NOT_LOGGED_IN_CODE)) {
            // TODO Give more notification to user that there RPC failed because they are not logged in?
            Window.Location.reload();
        }
        mainPanel.add(new Label("Error occurred"));
        mainPanel.add(new Label(e.getCode()));
        mainPanel.add(new Label(e.getDefaultMessage()));
        Button okButton = new Button("Ok");
        mainPanel.add(okButton);

        final PopupPanel popupPanel = PopupUtil.popup(mainPanel);

        okButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                PopupUtil.close(popupPanel);
            }

        });
    }

}
