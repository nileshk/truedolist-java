package com.myconnector.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.myconnector.service.TodoListService;
import com.myconnector.web.SubmitStringCommand;

public class TodoListsImportFromXmlController extends SimpleFormController {

	private TodoListService todoListService;
	
	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}
	
	public TodoListsImportFromXmlController() {
		super();
		setCommandClass(SubmitStringCommand.class);
	}
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		SubmitStringCommand submitStringCommand = (SubmitStringCommand) command;
		todoListService.importJaxbTodoLists(submitStringCommand.getString());
		return super.onSubmit(command);
	}
	
}
