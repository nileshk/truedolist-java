package com.myconnector.client.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.myconnector.client.domain.interfaces.ITodoList;

public class TwoTodoClientLists implements IsSerializable {

	private ITodoList todoList1;
	private ITodoList todoList2;

	public ITodoList getTodoList1() {
		return todoList1;
	}

	public void setTodoList1(ITodoList todoList1) {
		this.todoList1 = todoList1;
	}

	public ITodoList getTodoList2() {
		return todoList2;
	}

	public void setTodoList2(ITodoList todoList2) {
		this.todoList2 = todoList2;
	}

}
