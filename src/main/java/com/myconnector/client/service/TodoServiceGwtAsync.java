package com.myconnector.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.client.domain.TwoTodoClientItems;
import com.myconnector.client.domain.TwoTodoClientLists;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

/**
 * See {@link TodoServiceGwt} for non-asynchronous version of this
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface TodoServiceGwtAsync {

	void getTodoListsOnly(AsyncCallback<List<ITodoList>> callback);

	void getTodoListsFull(AsyncCallback<List<ITodoList>> callback);

	void getTodoItemsForList(Long todoListId, AsyncCallback<List<ITodoItem>> callback);

	void updateTodoItem(Long id, String title, AsyncCallback<Object> callback);

	void deleteTodoItem(Long id, AsyncCallback<Object> callback);

	void addTodoItem(Long todoListId, String title, AsyncCallback<Long> callback);

	void addTodoItemsFromTextArea(Long todoListId, String text,
			AsyncCallback<List<ITodoItem>> callback) throws ClientMessageException,
			ClientUnexpectedException;

	void deleteMultipleTodoItems(List<Long> itemIds, AsyncCallback<Object> callback);

	void moveTodoItem(Long todoItemId, Long destinationTodoListId,
			AsyncCallback<Object> callback);

	void repositionTodoItemBefore(Long todoItemId, Long beforeTodoItemId,
			AsyncCallback<Object> callback);

	void moveMultipleTodoItems(List<Long> itemIds, Long destinationTodoListId,
			AsyncCallback<Object> callback);

	void addTodoList(String title, AsyncCallback<ITodoList> addNewListCallback);

	void updateTodoListTitle(Long todoListId, String title, AsyncCallback<Object> callback);

	void moveTodoItemUp(Long id, AsyncCallback<TwoTodoClientItems> moveUpCallback);

	void moveTodoItemDown(Long id, AsyncCallback<TwoTodoClientItems> moveDownCallback);

	void deleteTodoList(Long id, AsyncCallback<Object> callback);

	void moveTodoListDown(Long todoListId, AsyncCallback<TwoTodoClientLists> asyncCallback);

	void moveTodoListUp(Long todoListId, AsyncCallback<TwoTodoClientLists> asyncCallback);

	void repositionTodoListBefore(Long todoListId, Long beforeTodoListId,
			AsyncCallback<Object> callback);

	void getTodoListsOneSelected(Long selectedTodoListId,
			AsyncCallback<List<ITodoList>> asyncCallback);

	void highlightTodoItem(Long todoItemId, boolean highlight, AsyncCallback<Object> callback);
}
