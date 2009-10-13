package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface ConfirmListener extends EventListener {

    void onYes(Widget sender);
    
    void onNo(Widget sender);
    
}
