package com.myconnector.client.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.myconnector.client.domain.interfaces.ITodoItem;

public class TodoItemClient implements IsSerializable, ITodoItem {

	private Long id;
	private String title;
	private Integer position;
	private Boolean highlighted;

	public TodoItemClient() {
	}

	public TodoItemClient(Long id, String title, Integer position) {
		super();
		this.id = id;
		this.title = title;
		this.position = position;
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(Boolean highlighted) {
		this.highlighted = highlighted;
	}

}
