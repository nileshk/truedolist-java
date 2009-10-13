package com.myconnector.client.composite;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.myconnector.client.TextConstants;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.model.DomainModel;
import com.myconnector.client.model.TodoListsChangeListener;

/**
 * Base class for Composites that interact with {@link DomainModel}. It implements
 * {@link TodoListsChangeListener} so subclasses only have to override the callback methods they
 * want to implement (rather than be cluttered with empty methods). It also contains a protected
 * instance of and setter for {@link DomainModel}.
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class BaseTodoComposite extends Composite implements TodoListsChangeListener, IDestroyable {

	protected DomainModel domainModel;
	protected TextConstants textConstants;
	
	public void setDomainModel(DomainModel domainModel) {
		this.domainModel = domainModel;
		domainModel.addTodoListsChangeListener(this);
	}
	
	public void setTextConstants(TextConstants textConstants) {
		this.textConstants = textConstants;
	}

	public void destroy() {
		domainModel.removeTodoListsChangeListener(this);
	}	
	
	public void onMultipleTodoItemsDelete(Object sender, ITodoList todoList, List<Long> itemIds) {

	}

	public void onMultipleTodoItemsMoved(Object sender, ITodoList todoList,
			Long destinationTodoListId, List<Long> itemIds) {

	}

	public void onTodoItemAdded(Object originator, ITodoList todoList, ITodoItem todoItem) {

	}

	public void onTodoItemDeleted(Object originator, ITodoItem todoItem) {

	}

	public void onTodoItemModified(Object originator, ITodoItem todoItem) {

	}

	public void onTodoItemMoveDown(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {

	}

	public void onTodoItemMoveUp(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {

	}

	public void onTodoItemMoved(ITodoList sourceTodoList, ITodoList destinationTodoList,
			ITodoItem todoItemToMove) {

	}

	public void onTodoItemRepositioned(ITodoItem todoItem, ITodoItem beforeTodoItem,
			ITodoList todoList) {

	}

	public void onTodoListAdded(Object originator, ITodoList todoList) {

	}

	public void onTodoListDeleted(Object originator, ITodoList todoList) {

	}

	public void onTodoListItemsFetched(Object originator, ITodoList todoList) {

	}

	public void onTodoListSelected(Object originator, ITodoList todoList) {

	}

	public void onTodoListTitleUpdated(Object sender, ITodoList todoList, String title) {

	}

	public void onTodoListRepositioned(ITodoList todoList, ITodoList beforeTodoList) {
	}

}