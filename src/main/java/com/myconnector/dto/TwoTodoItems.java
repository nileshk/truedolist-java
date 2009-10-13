package com.myconnector.dto;

import com.myconnector.client.domain.TwoTodoClientItems;
import com.myconnector.domain.TodoItem;

public class TwoTodoItems {

	private TodoItem todoItem1;
	private TodoItem todoItem2;

	public TodoItem getTodoItem1() {
		return todoItem1;
	}

	public void setTodoItem1(TodoItem todoItem1) {
		this.todoItem1 = todoItem1;
	}

	public TodoItem getTodoItem2() {
		return todoItem2;
	}

	public void setTodoItem2(TodoItem todoItem2) {
		this.todoItem2 = todoItem2;
	}
	
	public TwoTodoClientItems toTwoTodoClientItems() {
		TwoTodoClientItems twoTodoClientItems = new TwoTodoClientItems();
		twoTodoClientItems.setTodoItem1(todoItem1.toTodoItemClient());
		twoTodoClientItems.setTodoItem2(todoItem2.toTodoItemClient());
		return twoTodoClientItems;
	}

}
