package com.myconnector.service;

import java.util.List;

import com.myconnector.domain.TodoItem;
import com.myconnector.dto.TwoTodoItems;

public interface TodoItemService extends GenericChildService<TodoItem, Long>, GenericWithUserService<TodoItem, Long> {

    /**
     * Move item to the specified list
     * @param todoItemId
     * @param todoListId
     */
	void move(Long todoItemId, Long todoListId);
    
	/**
	 * Get an item by id, ensuring that its TodoList is loaded
	 * @param id
	 * @return
	 */
    TodoItem getByIdWithTodoList(Long id);

    /**
     * Update title of an item
     * @param id
     * @param title
     */
	void update(Long id, String title);

	/**
	 * Reposition item before a specified item
	 * @param todoItemId
	 * @param beforeTodoItemId
	 */
    void repositionBefore(Long todoItemId, Long beforeTodoItemId);
	
    /**
     * Move item up in its list
     * @param id
     */
    void moveUp(Long id);
    
    /**
     * Move item down in its list
     * @param id
     */
    void moveDown(Long id);
    
    /**
     * @param todoListId
     * @param title
     * @return generated id of new TodoItem
     */
    Long addTodoItem(Long todoListId, String title);

    void deleteMultiple(List<Long> itemIds);

    void moveMultiple(List<Long> itemIds, Long destinationTodoListId);

	TwoTodoItems moveUpWithReturn(Long id);

	TwoTodoItems moveDownWithReturn(Long id);

	/**
	 * Add a list of items that is separated by line feeds/carriage returns 
	 * @param todoListId ID of list to add items to
	 * @param text Text which is separated by line feeds/carriage returns to add
	 */
	void addTodoItemsFromText(Long todoListId, String text);

	com.myconnector.xml.todolists.TodoItem getJaxbTodoItem(Long id);

	void setHighlightTodoItem(Long todoItemId, boolean highlight);
}
