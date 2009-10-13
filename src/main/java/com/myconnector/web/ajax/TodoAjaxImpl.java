package com.myconnector.web.ajax;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.service.TodoItemService;
import com.myconnector.util.HttpSessionThreadLocal;

public class TodoAjaxImpl implements TodoAjax {

    static Logger logger = Logger.getLogger(TodoAjaxImpl.class);

    private TodoItemService todoItemService;

    public void setTodoItemService(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    public Collection<TodoItem> getList(Long todoListId, HttpSession httpSession) {
        logger.debug("TodoAjax.getList(" + todoListId);
        HttpSessionThreadLocal.set(httpSession);
        Collection<TodoItem> todoItems = todoItemService.getList(todoListId);
        return todoItems;
    }

    public void move(Long todoItemId, Long todoListId, HttpSession httpSession) {
        logger.debug("TodoAjax.move(" + todoItemId + ", " + todoListId + ")");
        HttpSessionThreadLocal.set(httpSession);
        todoItemService.move(todoItemId, todoListId);

    }

    public void save(String title, Long todoListId, HttpSession httpSession) {
        logger.debug("TodoAjax.save(" + title + ", " + todoListId + ")");
        HttpSessionThreadLocal.set(httpSession);
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle(title);
        todoItem.setTodoList(new TodoList(todoListId));
        todoItemService.save(todoItem);
    }

    public void update(String title, Long todoItemId, HttpSession httpSession) {
        logger.debug("TodoAjax.update(" + title + ", " + todoItemId + ")");
        HttpSessionThreadLocal.set(httpSession);
        // TODO use an update that only updates title where id
        TodoItem todoItem = todoItemService.getById(todoItemId);
        todoItem.setTitle(title);
        todoItemService.update(todoItem);        
    }

    public void repositionBefore(Long todoItemId, Long beforeTodoItemId) {
        if(logger.isDebugEnabled()) {
            logger.debug("TodoAjax.repositionBefore(" + todoItemId + ", " + beforeTodoItemId + ")");
        }
        todoItemService.repositionBefore(todoItemId, beforeTodoItemId);
    }
}

