package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.service.TodoListService;

public class TodoListsAllXmlController implements Controller {

	private String view;
	private TodoListService todoListService;

	public void setView(String view) {
		this.view = view;
	}

	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("xml", todoListService.getXmlForAllLists());
		return new ModelAndView(view, map);
	}

}
