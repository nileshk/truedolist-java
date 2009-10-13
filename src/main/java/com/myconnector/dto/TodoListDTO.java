package com.myconnector.dto;

import java.util.Set;

import com.myconnector.domain.TodoItem;

public interface TodoListDTO {

    public Long getId();

    public void setId(Long id);

    public String getTitle();

    public void setTitle(String title);

    public Set<TodoItem> getTodoItems();

    public void setTodoItems(Set<TodoItem> todoItems);

}
