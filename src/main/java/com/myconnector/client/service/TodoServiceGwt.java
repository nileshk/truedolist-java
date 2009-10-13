package com.myconnector.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.client.domain.TwoTodoClientItems;
import com.myconnector.client.domain.TwoTodoClientLists;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

/**
 * See {@link TodoServiceGwtAsync} for the asynchronous version of this interface.
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface TodoServiceGwt extends RemoteService {

	List<ITodoList> getTodoListsOnly() throws ClientMessageException, ClientUnexpectedException;

	List<ITodoList> getTodoListsFull() throws ClientMessageException, ClientUnexpectedException;

	List<ITodoList> getTodoListsOneSelected(Long selectedTodoListId)
			throws ClientMessageException, ClientUnexpectedException;

	List<ITodoItem> getTodoItemsForList(Long todoListId) throws ClientMessageException,
			ClientUnexpectedException;

	void updateTodoItem(Long id, String title) throws ClientMessageException,
			ClientUnexpectedException;

	void deleteTodoItem(Long id) throws ClientMessageException, ClientUnexpectedException;

	Long addTodoItem(Long todoListId, String title) throws ClientMessageException,
			ClientUnexpectedException;

	List<ITodoItem> addTodoItemsFromTextArea(Long todoListId, String text)
			throws ClientMessageException, ClientUnexpectedException;

	void deleteMultipleTodoItems(List<Long> itemIds) throws ClientMessageException,
			ClientUnexpectedException;

	void moveTodoItem(Long todoItemId, Long destinationTodoListId);
	
	void repositionTodoItemBefore(Long todoItemId, Long beforeTodoItemId);
	
	void moveMultipleTodoItems(List<Long> itemIds, Long destinationTodoListId)
			throws ClientMessageException, ClientUnexpectedException;

	ITodoList addTodoList(String title) throws ClientMessageException, ClientUnexpectedException;

	void updateTodoListTitle(Long todoListId, String title) throws ClientMessageException,
			ClientUnexpectedException;

	TwoTodoClientItems moveTodoItemUp(Long id) throws ClientMessageException,
			ClientUnexpectedException;

	TwoTodoClientItems moveTodoItemDown(Long id) throws ClientMessageException,
			ClientUnexpectedException;

	TwoTodoClientLists moveTodoListUp(Long todoListId) throws ClientMessageException,
			ClientUnexpectedException;

	TwoTodoClientLists moveTodoListDown(Long todoListId) throws ClientMessageException,
			ClientUnexpectedException;

	void repositionTodoListBefore(Long todoListId, Long beforeTodoListId);	
	
	void deleteTodoList(Long id) throws ClientMessageException, ClientUnexpectedException;
	
	void highlightTodoItem(Long todoItemId, boolean highlight);
}
