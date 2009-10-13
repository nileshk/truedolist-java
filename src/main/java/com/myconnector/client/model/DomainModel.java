package com.myconnector.client.model;

import java.util.List;

import com.myconnector.client.Reloadable;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

/**
 * {@link DomainModelImpl}
 * 
 * TODO remove sender parameters
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface DomainModel extends Reloadable {

	void init();

	void addTodoListsInitListener(TodoListsInitListener listener);

	void addTodoListsChangeListener(TodoListsChangeListener listener);

	void selectedTodoList(ITodoList todoList, Object sender);

	void deleteTodoList(Object sender, ITodoList todoList);

	void deleteTodoItem(Object sender, ITodoItem todoItem);

	void addTodoList(Object sender, String title);

	void updateTodoItemTitle(Object itemComposite, ITodoItem todoItem, String title);

	void moveTodoItemUp(Object sender, ITodoList todoList, ITodoItem todoItem);

	void moveTodoItemDown(Object sender, ITodoList todoList, ITodoItem todoItem);

	void moveTodoItem(ITodoList sourceTodoList, ITodoList destinationTodoList,
			ITodoItem todoItemToMove);
	
	void repositionTodoItemBefore(ITodoItem todoItem, ITodoItem beforeTodoItem, ITodoList todoList);

	void fetchTodoItemsForList(Object sender, ITodoList todoList);

	void addTodoItem(Object sender, ITodoList todoList, String title);

	void deleteMultipleTodoItems(Object sender, ITodoList todoList, List<Long> itemIds);

	void moveMultipleTodoItems(Object sender, ITodoList todoList, Long destinationTodoListId,
			List<Long> itemIds);

	void updateTodoListTitle(Object sender, ITodoList todoList, String title);

	void removeTodoListsChangeListener(TodoListsChangeListener listener);

	void repositionTodoListBefore(ITodoList todoList, ITodoList beforeTodoList);	
	
	void importTextList(ITodoList todoList, String text);

	void setHighlightTodoItem(Object sender, ITodoItem todoItem, boolean highlight);
}
