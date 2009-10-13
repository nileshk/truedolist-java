package com.myconnector.client.listeners;

import java.util.EventListener;

import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.domain.interfaces.ITodoItem;

public interface TodoItemMoveListener extends EventListener {

	void onMoveUp(Widget sender, ITodoItem todoItem1, ITodoItem todoItem2);

	void onMoveDown(Widget sender, ITodoItem todoItem1, ITodoItem todoItem2);

}
