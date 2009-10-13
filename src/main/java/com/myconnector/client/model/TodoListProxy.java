package com.myconnector.client.model;

import java.util.ArrayList;
import java.util.List;

import com.myconnector.client.domain.interfaces.ITodoContext;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

public class TodoListProxy implements ITodoList {

	private ITodoList todoList;

	public TodoListProxy(ITodoList todoList) {
		this.todoList = todoList;
	}
	
	public List<ITodoItem> getTodoItems() {
		return todoList.getTodoItems();
	}

	public void setTodoItems(List<ITodoItem> todoItems) {
		List<ITodoItem> list = new ArrayList<ITodoItem>();
		for (ITodoItem todoItem : todoItems) {
			TodoItemProxy todoItemProxy = new TodoItemProxy(todoItem);
			list.add(todoItemProxy);
		}
		todoList.setTodoItems(list);
	}

	public boolean isTodoItemsLoaded() {
		return todoList.isTodoItemsLoaded();
	}

	public void setTodoItemsLoaded(boolean todoItemsLoaded) {
		todoList.setTodoItemsLoaded(todoItemsLoaded);
	}

	public Long getId() {
		return todoList.getId();
	}

	public void setId(Long id) {
		todoList.setId(id);
	}

	public String getTitle() {
		return todoList.getTitle();
	}

	public void setTitle(String title) {
		todoList.setTitle(title);
	}

	public Integer getPosition() {
		return todoList.getPosition();
	}

	public void setPosition(Integer position) {
		todoList.setPosition(position);
	}

	public ITodoContext getContext() {
		return todoList.getContext();
	}

	public void setContext(ITodoContext context) {
		todoList.setContext(context);
	}

}
