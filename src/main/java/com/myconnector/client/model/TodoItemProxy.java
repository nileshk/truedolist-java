package com.myconnector.client.model;

import com.myconnector.client.domain.interfaces.ITodoItem;

public class TodoItemProxy implements ITodoItem {

	private ITodoItem todoItem;

	public TodoItemProxy(ITodoItem todoItem) {
		this.todoItem = todoItem;
	}

	public Long getId() {
		return todoItem.getId();
	}

	public void setId(Long id) {
		todoItem.setId(id);
	}

	public String getTitle() {
		return todoItem.getTitle();
	}

	public void setTitle(String title) {
		todoItem.setTitle(title);
	}

	public Integer getPosition() {
		return todoItem.getPosition();
	}

	public void setPosition(Integer position) {
		todoItem.setPosition(position);
	}

	public Boolean getHighlighted() {
		return todoItem.getHighlighted();
	}

	public void setHighlighted(Boolean highlighted) {
		todoItem.setHighlighted(highlighted);
	}

}
