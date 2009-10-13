package com.myconnector.webservice;

import java.util.List;

import com.myconnector.dto.TodoListDTO;
import com.myconnector.dto.TodoListSimpleDTO;

public interface TodoWebService extends BaseWS {
    
    public List<TodoListSimpleDTO> getTodoLists();
    
    public TodoListDTO getCompleteListById(Long id);
    
    /***
     * 
     * @param title
     * @return id of newly created todo list
     */
    public Long addTodoList(String title);
    
    /**
     * 
     * @param todoListId
     * @param title
     * @return id of newly created todo item
     */
    public Long addTodoItem(Long todoListId, String title);
    
    public void updateTodoList(Long id, String title);
    
    public void updateTodoItem(Long id, String title);
    
    public void moveTodoItem(Long todoItemId, Long destinationTodoListId);
    
//    public TodoDTO getTodo(String id);
//    public void saveTodo(TodoDTO todo);
//    public void updateTodo(TodoDTO todo);
//    public void deleteTodoById(String id);
//    public TodoDTO[] getTodoList();
//    public TodoDTO[] getTodoListSortedBy(String orderBy, boolean descending);    

}
