package com.myconnector.web.rest;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.myconnector.service.TodoItemService;
import com.myconnector.service.TodoListService;
import com.myconnector.xml.todolists.TodoItem;
import com.myconnector.xml.todolists.TodoList;
import com.myconnector.xml.todolists.TodoLists;

@Path("/todo/")
@ProduceMime("application/xml")
public class TodoRestServiceImpl {

	static Logger logger = Logger.getLogger(TodoRestServiceImpl.class);

	private TodoItemService todoItemService;
	private TodoListService todoListService;
	@Context
	UriInfo uriInfo;

	private UriBuilder getUriBuilder() {
		return UriBuilder.fromUri(RestConstants.BASE_URL);
	}
	
	public void setTodoItemService(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}

	@GET
	@Path("/lists")
	public TodoLists getTodoLists() {
		logger.debug("AbsolutePath: " + uriInfo.getAbsolutePath());
		logger.debug("Path: " + uriInfo.getPath());
		logger.debug("Base URI: " + uriInfo.getBaseUri().toASCIIString());
		TodoLists todoLists = todoListService.getJaxbTodoLists();
		for (TodoList todoList : todoLists.getTodoList()) {
			UriBuilder ub = getUriBuilder();
			URI todoListUri = ub.path(RestConstants.BASE_PATH).path("todo").path("lists").path(
					todoList.getId().toString()).build();
			todoList.setHref(todoListUri.toASCIIString());
			// logger.debug(todoListUri.toASCIIString());
		}
		return todoLists;
	}

	@GET
	@Path("/lists/{id}")
	public TodoList getTodoList(@PathParam("id")
	Long id) {
		TodoList todoList = todoListService.getJaxbTodoListComplete(id);
		for (TodoItem item : todoList.getTodoItem()) {
			UriBuilder ub = getUriBuilder();
			URI todoItemUri = ub.path(RestConstants.BASE_PATH).path("todo").path("items").path(
					item.getId().toString()).build();
			item.setHref(todoItemUri.toASCIIString());
		}
		return todoList;
	}

	@POST
	@Path("/lists")
	public Response addTodoList(TodoList todoList) {
		if (logger.isDebugEnabled() && todoList != null) {
			logger.debug("Adding todoList with title = " + todoList.getTitle());
		}
		todoListService.addTodoList(todoList.getTitle());
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("/lists/{id}")
	public Response updateTodoList(@PathParam("id")
	Long id, TodoList todoList) {
		todoListService.update(id, todoList.getTitle());
		return Response.status(Status.ACCEPTED).build();
	}

	@DELETE
	@Path("/lists/{id}")
	public Response deleteTodoList(@PathParam("id")
	Long id) {
		logger.debug("Deleting todoList with id = " + id);
		todoListService.deleteById(id);
		return Response.status(Status.OK).build();
	}

	@GET
	@Path("/items/{id}")
	public TodoItem getTodoItem(@PathParam("id")
	Long id) {
		return todoItemService.getJaxbTodoItem(id);
	}

	@POST
	@Path("/lists/{id}")
	public Response addTodoItem(@PathParam("id")
	Long id, TodoItem todoItem) {
		todoItemService.addTodoItem(id, todoItem.getTitle());
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("/items/{id}")
	public Response updateTodoItem(@PathParam("id")
	Long id, TodoItem todoItem) {
		logger.debug("updateTodoItem: id = " + id);
		todoItemService.update(id, todoItem.getTitle());
		return Response.status(Status.ACCEPTED).build();
	}

	@DELETE
	@Path("/items/{id}")
	public Response deleteTodoItem(@PathParam("id")
	Long id) {
		logger.debug("Deleting todoItem with id = " + id);
		todoItemService.deleteById(id);
		return Response.status(Status.OK).build();
	}
}
