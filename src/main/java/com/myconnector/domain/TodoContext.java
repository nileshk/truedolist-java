package com.myconnector.domain;

import java.io.Serializable;

import com.myconnector.client.domain.TodoContextClient;
import com.myconnector.client.domain.interfaces.ITodoContext;
import com.myconnector.domain.interfaces.HasPosition;
import com.myconnector.domain.interfaces.HasTitle;
import com.myconnector.domain.interfaces.HasUserData;

public class TodoContext implements Serializable, HasUserData, HasPosition, HasTitle {

	private static final long serialVersionUID = 1L;
	protected int hashCode = Integer.MIN_VALUE;

	private Long id;
	private String title;
	private UserData userData;
	private Integer position;

	public TodoContext() {
		super();
	}

	public TodoContext(Long id) {
		this.setId(id);
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof TodoContext))
			return false;
		else {
			TodoContext todo = (TodoContext) obj;
			if (null == this.getId() || null == todo.getId())
				return false;
			else
				return (this.getId().equals(todo.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
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

	public TodoContextClient toTodoContextClient() {
		TodoContextClient t = new TodoContextClient();
		t.setId(getId());
		t.setPosition(getPosition());
		t.setTitle(getTitle());
		return t;
	}

}
