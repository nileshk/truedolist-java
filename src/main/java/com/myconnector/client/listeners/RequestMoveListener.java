package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface RequestMoveListener extends EventListener {

    void onRequestMoveUp(Widget sender);
    
    void onRequestMoveDown(Widget sender);
    
}
