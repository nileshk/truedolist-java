package com.myconnector.client;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;

public interface CheckBoxChangeListener extends EventListener {

    void onCheckBoxChange(Widget sender, boolean checked);
    
}
