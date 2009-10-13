package com.myconnector.client.composite;

import com.myconnector.client.AsWidget;
import com.myconnector.client.domain.interfaces.ITodoList;

public interface ITodoListComposite extends AsWidget, IDestroyable {

	void select();

	ITodoList getTodoList();

	void unselect();

	void refresh();

}
