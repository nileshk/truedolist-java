package com.myconnector.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupUtil {

    public static PopupPanel popup(Widget widget) {
        final PopupPanel popupPanel = new PopupPanel(false, true);
        popupPanel.add(widget);
        popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
                int left = (Window.getClientWidth() - offsetWidth) / 3;
                int top = (Window.getClientHeight() - offsetHeight) / 3;
                popupPanel.setPopupPosition(left, top);
            }
        });
        return popupPanel;
        
    }
    
    public static void close(PopupPanel popupPanel) {
        popupPanel.hide();
        popupPanel.clear();
        popupPanel.removeFromParent();
    }
    
}
