package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class TodoItemMoveController extends SimpleFormController implements
		Controller {

	static Logger logger = Logger.getLogger(TodoItemMoveController.class);

	protected TodoItemService todoItemService;
	protected TodoListService todoListService;

	public void setTodoItemService(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}

	public TodoItemMoveController() {
		setCommandClass(TodoItem.class);
	}
	
	@SuppressWarnings("unchecked")
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Long id = Long.valueOf(request.getParameter("id"));
        TodoItem todoItem = todoItemService.getByIdWithTodoList(id);        
		return todoItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlMap)
			throws Exception {
		List<TodoList> todoLists = todoListService.getListForCurrentUser();
        Long id = Long.valueOf(request.getParameter("id"));
        TodoItem todoItem = todoItemService.getById(id);
        if(controlMap == null) {
            controlMap = new HashMap();
        }
		controlMap.put("todoLists", todoLists);
        controlMap.put("todoItem", todoItem);
		return super.showForm(request, response, errors, controlMap);
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		TodoItem todoItem = (TodoItem) command;
		if (todoItem != null && todoItem.getTodoList() != null) {
		    todoItemService.move(todoItem.getId(), todoItem.getTodoList().getId());
        }
		String successView = getSuccessView() + "?id="
				+ todoItem.getTodoList().getId();
		return new ModelAndView(new RedirectView(successView, false));
	}
}