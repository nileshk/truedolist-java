package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface EditListener extends EventListener {

    void onEdit(Widget sender);

    void onFinishedEdit(Widget sender);
}
