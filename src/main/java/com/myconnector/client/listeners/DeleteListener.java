package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface DeleteListener extends EventListener {

    void onDelete(Widget sender, String id);
    
}
