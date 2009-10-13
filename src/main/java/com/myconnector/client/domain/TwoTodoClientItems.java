package com.myconnector.client.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.myconnector.client.domain.interfaces.ITodoItem;

public class TwoTodoClientItems implements IsSerializable {

	private ITodoItem todoItem1;
	private ITodoItem todoItem2;

	public ITodoItem getTodoItem1() {
		return todoItem1;
	}

	public void setTodoItem1(ITodoItem todoItem1) {
		this.todoItem1 = todoItem1;
	}

	public ITodoItem getTodoItem2() {
		return todoItem2;
	}

	public void setTodoItem2(ITodoItem todoItem2) {
		this.todoItem2 = todoItem2;
	}

}
