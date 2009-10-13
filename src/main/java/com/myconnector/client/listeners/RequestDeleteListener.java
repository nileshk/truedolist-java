package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface RequestDeleteListener extends EventListener {

    void onRequestDelete(Widget sender);
    
}
