package com.myconnector.dto;

import com.myconnector.client.domain.TwoTodoClientLists;
import com.myconnector.domain.TodoList;

public class TwoTodoLists {

    private TodoList todoList1;
    private TodoList todoList2;

    public TodoList getTodoList1() {
        return todoList1;
    }

    public void setTodoList1(TodoList todoList1) {
        this.todoList1 = todoList1;
    }

    public TodoList getTodoList2() {
        return todoList2;
    }

    public void setTodoList2(TodoList todoList2) {
        this.todoList2 = todoList2;
    }

    public TwoTodoClientLists toTwoTodoClientLists() {
        TwoTodoClientLists twoTodoClientLists = new TwoTodoClientLists();
        twoTodoClientLists.setTodoList1(todoList1.toTodoListClientBare());
        twoTodoClientLists.setTodoList2(todoList2.toTodoListClientBare());
        return twoTodoClientLists;
    }

}
