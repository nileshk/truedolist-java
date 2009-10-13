package com.myconnector.web.ajax;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import com.myconnector.domain.TodoItem;

public interface TodoAjax {
    
    public Collection<TodoItem> getList(Long todoListId, HttpSession httpSession);
    
    public void move(Long todoItemId, Long todoListId, HttpSession httpSession);

    public void save(String title, Long todoListId, HttpSession httpSession);

    public void update(String title, Long todoItemId, HttpSession httpSession);
    
    public void repositionBefore(Long todoItemId, Long beforeTodoItemId);
}
