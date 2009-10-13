package com.myconnector.client;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageBox {

    public static void show(String message) {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(new Label(message));
        Button okButton = new Button("Ok");
        vPanel.add(okButton);

        final PopupPanel popupPanel = PopupUtil.popup(vPanel);

        okButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                PopupUtil.close(popupPanel);
            }

        });

    }

    public static void show(List<String> messages) {
        VerticalPanel hPanel = new VerticalPanel();
        for (String message : messages) {
            hPanel.add(new Label(message));
        }        
        Button okButton = new Button("Ok");
        hPanel.add(okButton);

        final PopupPanel popupPanel = PopupUtil.popup(hPanel);

        okButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                PopupUtil.close(popupPanel);
            }

        });

    }

}
