package com.myconnector.service;

import com.myconnector.domain.TodoList;
import com.myconnector.dto.TwoTodoLists;

/**
 * See {@link TodoListServiceImpl} for implementation
 *
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface TodoListService extends GenericWithUserService<TodoList, Long> {

    Long addTodoItem(Long todoListId, String title);

	void update(Long id, String title);

	void moveUp(Long id);
	
	void moveDown(Long id);
	
	TodoList getByIdWithItems(Long id);

    TodoList addTodoList(String title);

    TwoTodoLists moveDownWithReturn(Long todoListId);

    TwoTodoLists moveUpWithReturn(Long todoListId);
	
    void repositionBefore(Long todoListId, Long beforeTodoListId);

    com.myconnector.xml.todolists.TodoLists getJaxbTodoLists();
    
    com.myconnector.xml.todolists.TodoLists getJaxbTodoListsComplete();
    
    com.myconnector.xml.todolists.TodoList getJaxbTodoList(Long todoListId);
    
    com.myconnector.xml.todolists.TodoList getJaxbTodoListComplete(Long todoListId);
    
    String getXmlForAllLists();
    
    String getXmlForList(Long todoListId);
    
    void importJaxbTodoLists(String todoListsXml);
}
