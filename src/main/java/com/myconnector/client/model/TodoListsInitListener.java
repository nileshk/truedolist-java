package com.myconnector.client.model;

import java.util.EventListener;
import java.util.List;

import com.myconnector.client.domain.interfaces.ITodoList;

public interface TodoListsInitListener extends EventListener {

	void onTodoListsInit(List<ITodoList> todoLists);
	
}
