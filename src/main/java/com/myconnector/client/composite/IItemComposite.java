package com.myconnector.client.composite;

import com.myconnector.client.AsWidget;
import com.myconnector.client.CheckBoxChangeListener;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.listeners.EditListener;
import com.myconnector.client.listeners.TodoItemMoveListener;

public interface IItemComposite extends AsWidget, IDestroyable {

	void addEditListener(EditListener listener);

	void addCheckBoxChangeListener(CheckBoxChangeListener listener);

	void addTodoItemMoveListener(TodoItemMoveListener listener);

	void disableEdit();

	void edit();

	Long getId();

	ITodoItem getTodoItem();
	
	ITodoList getTodoList();

}
