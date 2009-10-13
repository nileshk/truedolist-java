package com.myconnector.client.domain.interfaces;

import java.util.List;

public interface ITodoList extends HasId, HasTitle, HasPosition, HasContext {

	List<ITodoItem> getTodoItems();

	void setTodoItems(List<ITodoItem> todoItems);

	boolean isTodoItemsLoaded();

	void setTodoItemsLoaded(boolean todoItemsLoaded);
	
}
