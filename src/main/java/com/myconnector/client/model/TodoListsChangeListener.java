package com.myconnector.client.model;

import java.util.EventListener;
import java.util.List;

import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

public interface TodoListsChangeListener extends EventListener {

	void onTodoListSelected(Object originator, ITodoList todoList);

	void onTodoListDeleted(Object originator, ITodoList todoList);

	void onTodoItemDeleted(Object originator, ITodoItem todoItem);

	void onTodoListAdded(Object originator, ITodoList todoList);

	void onTodoItemModified(Object originator, ITodoItem todoItem);

	void onTodoItemMoveUp(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2);

	void onTodoItemMoveDown(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2);

	void onTodoListItemsFetched(Object originator, ITodoList todoList);

	void onTodoItemAdded(Object originator, ITodoList todoList, ITodoItem todoItem);

	void onMultipleTodoItemsDelete(Object sender, ITodoList todoList, List<Long> itemIds);

	void onMultipleTodoItemsMoved(Object sender, ITodoList todoList, Long destinationTodoListId,
			List<Long> itemIds);

	void onTodoListTitleUpdated(Object sender, ITodoList todoList, String title);

	void onTodoItemMoved(ITodoList sourceTodoList, ITodoList destinationTodoList,
			ITodoItem todoItemToMove);

	void onTodoItemRepositioned(ITodoItem todoItem, ITodoItem beforeTodoItem, ITodoList todoList);

	void onTodoListRepositioned(ITodoList todoList, ITodoList beforeTodoList);
}
