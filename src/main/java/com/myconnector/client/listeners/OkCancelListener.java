package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface OkCancelListener extends EventListener {

    void onOk(Widget sender);
    
    void onCancel(Widget sender);

    
}
