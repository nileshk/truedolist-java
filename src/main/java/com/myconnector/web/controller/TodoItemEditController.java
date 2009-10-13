package com.myconnector.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.service.TodoItemService;
import com.myconnector.service.TodoListService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class TodoItemEditController extends SimpleFormController implements Controller {

    static Logger logger = Logger.getLogger(TodoItemEditController.class);

    protected TodoItemService service;
    protected TodoListService todoListService;

    public void setService(TodoItemService service) {
        this.service = service;
    }

    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @SuppressWarnings("unchecked")
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        TodoItem command = null;
        if (id == null || id.equals("")) {
            command = new TodoItem();
        } else {
            command = service.getById(Long.valueOf(id));
        }
        String parentId = request.getParameter("parentId");
        if (command.getTodoList() == null) {
            TodoList todoList = new TodoList();
            if(parentId == null || parentId.length() == 0) {
            	todoList.setId(null);
            } else {
            	todoList.setId(Long.valueOf(parentId));
            }
            command.setTodoList(todoList);
        }
        return command;
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            Object command, BindException errors) throws Exception {
        TodoItem todoItem = (TodoItem) command;
        Long id = null;
        id = todoItem.getId();
        if (id == null) {
            service.save(todoItem);
        } else {
            service.update(todoItem);
        }
        String successView = getSuccessView() + "?id=" + todoItem.getTodoList().getId();
        return new ModelAndView(new RedirectView(successView, false));
    }
}