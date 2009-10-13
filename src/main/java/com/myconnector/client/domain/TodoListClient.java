package com.myconnector.client.domain;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.myconnector.client.domain.interfaces.ITodoContext;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

public class TodoListClient implements IsSerializable, ITodoList {

	private Long id;
	private String title;
	private boolean todoItemsLoaded = false;
	private List<ITodoItem> todoItems;
	private Integer position;
	private ITodoContext context;

	public TodoListClient() {
	}

	public TodoListClient(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ITodoItem> getTodoItems() {
		return todoItems;
	}

	public void setTodoItems(List<ITodoItem> todoItems) {
		this.todoItems = todoItems;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public boolean isTodoItemsLoaded() {
		return todoItemsLoaded;
	}

	public void setTodoItemsLoaded(boolean todoItemsLoaded) {
		this.todoItemsLoaded = todoItemsLoaded;
	}

	public ITodoContext getContext() {
		return context;
	}

	public void setContext(ITodoContext context) {
		this.context = context;
	}

}
