package com.myconnector.webservice;

import java.util.ArrayList;
import java.util.List;

import com.myconnector.domain.TodoList;
import com.myconnector.dto.TodoListDTO;
import com.myconnector.dto.TodoListSimpleDTO;
import com.myconnector.service.TodoItemService;
import com.myconnector.service.TodoListService;

public class TodoWebServiceImpl extends BaseWSImpl implements TodoWebService {

    private TodoItemService todoItemService;
    private TodoListService todoListService;

    public void setTodoItemService(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    public Long addTodoItem(Long todoListId, String title) {
        return todoListService.addTodoItem(todoListId, title);
    }

    public Long addTodoList(String title) {
        TodoList obj = new TodoList();
        obj.setTitle(title);
        todoListService.save(obj);
        // TODO return id of new element
        return null;
    }

    public TodoListDTO getCompleteListById(Long id) {
    	TodoList todoList = todoListService.getByIdForCurrentUser(id);
        return todoList;
    }

    public List<TodoListSimpleDTO> getTodoLists() {
        List<TodoList> list = todoListService.getListForCurrentUser();
        List<TodoListSimpleDTO> returnList = new ArrayList<TodoListSimpleDTO>();
        returnList.addAll(list);
        return returnList;
    }

    public void moveTodoItem(Long todoItemId, Long destinationTodoListId) {
        todoItemService.move(todoItemId, destinationTodoListId);
    }

    public void updateTodoItem(Long id, String title) {
        todoItemService.update(id, title);

    }

    public void updateTodoList(Long id, String title) {
        todoListService.update(id, title);
    }
}
