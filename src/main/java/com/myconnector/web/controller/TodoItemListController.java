package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.service.TodoListService;

public class TodoItemListController implements Controller {

    static Logger logger = Logger.getLogger(GenericListController.class);

    private TodoListService todoListService;

    protected String view;

    protected String listName = "list";

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setTodoListService(TodoListService genericService) {
        this.todoListService = genericService;
    }
    
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {        
        Map<String, Object> model = new HashMap<String, Object>();
        Long todoListId = Long.valueOf(request.getParameter("id"));
        model.put("id", todoListId);
        TodoList todoList = todoListService.getByIdWithItems(todoListId);
        model.put(listName, todoList.getTodoItems());
        model.put("todoList", todoList);

        TodoItem command = new TodoItem();
        command.setTodoList(todoList);
        model.put("command", command);
        return new ModelAndView(view, model);
    }
    
}
