package com.myconnector.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.myconnector.dao.AbstractDAO;
import com.myconnector.dao.TodoItemDAO;
import com.myconnector.dao.TodoListDAO;
import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.dto.TwoTodoItems;
import com.myconnector.util.ConvertUtil;

public class TodoItemServiceImpl extends GenericWithUserServiceImpl<TodoItem, Long> implements
		TodoItemService {

	static Logger logger = Logger.getLogger(TodoItemServiceImpl.class);

	private TodoItemDAO todoItemDAO;
	private TodoListDAO todoListDAO;
	private TodoListService todoListService;

	@Override
	protected AbstractDAO<TodoItem, Long> getGenericDAO() {
		return todoItemDAO;
	}

	public void setTodoItemDAO(TodoItemDAO todoItemDAO) {
		this.todoItemDAO = todoItemDAO;
	}

	public void setTodoListDAO(TodoListDAO todoListDAO) {
		this.todoListDAO = todoListDAO;
	}

	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}

	@Override
	public Long save(TodoItem obj) {
		if (setPositionIfNull(obj)) {
			return super.save(obj);
		}
		return null;
	}

	@Override
	public void update(TodoItem obj) {
		if (setPositionIfNull(obj)) {
			super.update(obj);
		}
	}

	private boolean setPositionIfNull(TodoItem obj) {
		if (obj.getPosition() == null) {
			// Get the next position
			/*
			 * TODO This whole block of code is horribly inefficient and needs to be replaced,
			 * surely we can just do a database query that returns the next position?
			 */
			// XXX What if todo list is null?
			TodoList todoList = todoListService.getById(obj.getTodoList().getId());
			int position = 0;
			TodoItem itemToUpdate = null;
			for (TodoItem item : todoList.getTodoItems()) {
				if (obj.equals(item)) {
					itemToUpdate = item;
				}
				if (item.getPosition() != null
						&& item.getPosition().compareTo(Integer.valueOf(position)) >= 0) {
					position = item.getPosition().intValue() + 1;
				}
			}
			if (itemToUpdate != null) {
				itemToUpdate.setPosition(position);
				itemToUpdate.setTitle(obj.getTitle());
				if (!itemToUpdate.getTodoList().equals(obj.getTodoList())) {
					itemToUpdate.setTodoList(obj.getTodoList());
				}
				return false;
			}

			obj.setPosition(position);
		}
		return true;
	}

	public Collection<TodoItem> getList(Long parentId) {
		TodoList todoList = todoListDAO.load(parentId);
		assertObjectBelongsToCurrentUser(todoList);
		int size = todoList.getTodoItems().size();
		logger.debug("number of TodoItems: " + size + " for todoList.id = " + parentId);
		return todoList.getTodoItems();
	}

	public void move(Long todoItemId, Long todoListId) {
		TodoItem todoItem = getById(todoItemId);
		// use service instead?
		TodoList todoList = todoListDAO.load(todoListId);
		assertObjectBelongsToCurrentUser(todoList);
		todoItem.setTodoList(todoList);
		todoItem.setPosition(null);
		save(todoItem);
	}

	public TodoItem getByIdWithTodoList(Long id) {
		TodoItem todoItem = getById(id);
		todoItem.getTodoList();
		return todoItem;
	}

	public void update(Long id, String title) {
		TodoItem todoItem = getById(id);
		todoItem.setTitle(title);
		super.update(todoItem);
	}

	public void repositionBefore(Long todoItemId, Long beforeTodoItemId) {
		TodoItem todoItem = getById(todoItemId);
		Set<TodoItem> list = todoItem.getTodoList().getTodoItems();
		boolean beforeItemFound = false;
		int positionNumber = 0;
		for (TodoItem item : list) {
			if (!item.equals(todoItem)) {
				if (beforeItemFound == true) {
					positionNumber++;
					item.setPosition(positionNumber + 1);
					todoItemDAO.save(item);
				} else if (item.getId().equals(beforeTodoItemId)) {
					todoItem.setPosition(item.getPosition().intValue());
					logger.debug("Modifying item to move: Title: " + todoItem.getTitle()
							+ ", Position: " + todoItem.getPosition());
					todoItemDAO.save(todoItem);
					beforeItemFound = true;
					positionNumber = item.getPosition().intValue() + 1;
					item.setPosition(positionNumber);
					todoItemDAO.save(item);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Title: " + item.getTitle() + ", Position: " + item.getPosition());
			}
		}
		// XXX Should we throw an error if something wasn't found?

	}

	public void moveDown(Long id) {
		boolean moveUp = false;
		moveUpOrDown(id, moveUp);
	}

	public void moveUp(Long id) {
		boolean moveUp = true;
		moveUpOrDown(id, moveUp);
	}

	private void moveUpOrDown(Long id, boolean moveUp) {
		TodoItem itemToMove = getByIdForCurrentUser(id);
		Set<TodoItem> list = itemToMove.getTodoList().getTodoItems();
		TodoItem[] array = new TodoItem[list.size()];
		list.toArray(array);
		for (int i = 0; i < list.size(); i++) {
			TodoItem item = array[i];
			if (item.getId().equals(id)) {
				TodoItem adjacentItem;
				if (moveUp) {
					if (i == 0) {
						return;
					}
					adjacentItem = array[i - 1];
				} else {
					if (i == list.size() - 1) {
						return;
					}
					adjacentItem = array[i + 1];
				}
				Integer positionBefore = adjacentItem.getPosition();
				adjacentItem.setPosition(item.getPosition());
				item.setPosition(positionBefore);
				// return?
			}
		}
	}

	public Long addTodoItem(Long todoListId, String title) {
		TodoList todoList = todoListDAO.load(todoListId);
		assertObjectBelongsToCurrentUser(todoList);
		TodoItem todoItem = new TodoItem();
		todoItem.setTitle(title);
		todoList.addTodoItem(todoItem);
		return save(todoItem);
	}

	public void deleteMultiple(List<Long> itemIds) {
		for (Long id : itemIds) {
			deleteById(id);
		}
	}

	public void moveMultiple(List<Long> itemIds, Long destinationTodoListId) {
		for (Long id : itemIds) {
			move(id, destinationTodoListId);
		}
	}

	private TwoTodoItems moveUpOrDownWithReturn(Long id, boolean moveUp) {
		TodoItem itemToMove = getByIdForCurrentUser(id);
		Set<TodoItem> list = itemToMove.getTodoList().getTodoItems();
		TodoItem[] array = new TodoItem[list.size()];
		TwoTodoItems twoTodoItems = new TwoTodoItems();
		list.toArray(array);
		for (int i = 0; i < list.size(); i++) {
			TodoItem item = array[i];
			if (item.getId().equals(id)) {
				TodoItem adjacentItem;
				if (moveUp) {
					if (i == 0) {
						return null;
					}
					adjacentItem = array[i - 1];
				} else {
					if (i == list.size() - 1) {
						return null;
					}
					adjacentItem = array[i + 1];
				}
				Integer positionBefore = adjacentItem.getPosition();
				adjacentItem.setPosition(item.getPosition());
				item.setPosition(positionBefore);
				twoTodoItems.setTodoItem1(adjacentItem);
				twoTodoItems.setTodoItem2(item);
				return twoTodoItems;
			}
		}
		return null;
	}

	public TwoTodoItems moveDownWithReturn(Long id) {
		TwoTodoItems twoTodoItems = moveUpOrDownWithReturn(id, false);
		return twoTodoItems;
	}

	public TwoTodoItems moveUpWithReturn(Long id) {
		TwoTodoItems twoTodoItems = moveUpOrDownWithReturn(id, true);
		return twoTodoItems;
	}

	public void addTodoItemsFromText(Long todoListId, String text) {
		List<String> itemList = ConvertUtil.convertTextToStringList(text);
		if (itemList != null && itemList.size() > 0) {
			TodoList todoList = todoListDAO.load(todoListId);
			assertObjectBelongsToCurrentUser(todoList);
			for (String title : itemList) {
				if (title.startsWith("* ")) {
					title = title.substring(2);
				}
				TodoItem todoItem = new TodoItem();
				todoItem.setTitle(title);
				todoList.addTodoItem(todoItem);
				logger.debug("Added item with title: " + title);
				save(todoItem);
			}
		}
	}

	private com.myconnector.xml.todolists.TodoItem toTodoItemJaxb(TodoItem todoItem) {
		com.myconnector.xml.todolists.TodoItem todoListXml = new com.myconnector.xml.todolists.TodoItem();
		todoListXml.setId(todoItem.getId());
		todoListXml.setTitle(todoItem.getTitle());
		return todoListXml;		
	}

	public com.myconnector.xml.todolists.TodoItem getJaxbTodoItem(Long id) {
		return toTodoItemJaxb(getByIdForCurrentUser(id));
	}

	@Override
	public void setHighlightTodoItem(Long todoItemId, boolean highlight) {
		TodoItem todoItem = getById(todoItemId);
		todoItem.setHighlighted(highlight);
	}

}
