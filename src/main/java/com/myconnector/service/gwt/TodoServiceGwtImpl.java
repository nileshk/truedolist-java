package com.myconnector.service.gwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.client.domain.TwoTodoClientItems;
import com.myconnector.client.domain.TwoTodoClientLists;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.service.TodoServiceGwt;
import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.dto.TwoTodoItems;
import com.myconnector.dto.TwoTodoLists;
import com.myconnector.gwt.GwtRpcEndPoint;
import com.myconnector.service.TodoItemService;
import com.myconnector.service.TodoListService;

@Controller
@GwtRpcEndPoint
public class TodoServiceGwtImpl extends AbstractBaseServiceGwtImpl implements TodoServiceGwt {

	private TodoListService todoListService;
	private TodoItemService todoItemService;

	public void setTodoListService(TodoListService todoListService) {
		this.todoListService = todoListService;
	}

	public void setTodoItemService(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	public List<ITodoItem> getTodoItemsForList(Long todoListId) {
		try {
			TodoList todoList = todoListService.getByIdWithItems(todoListId);
			List<ITodoItem> todoItems = new ArrayList<ITodoItem>();
			for (TodoItem item : todoList.getTodoItems()) {
				todoItems.add(item.toTodoItemClient());
			}
			return todoItems;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public List<ITodoList> getTodoListsOnly() {
		try {
			List<TodoList> list = todoListService.getListForCurrentUser();
			List<ITodoList> todoListsClient = new ArrayList<ITodoList>();
			for (TodoList todoList : list) {
				todoListsClient.add(todoList.toTodoListClientBare());
			}
			return todoListsClient;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public List<ITodoList> getTodoListsFull() {
		try {
			List<TodoList> list = todoListService.getListForCurrentUser();
			List<ITodoList> todoListsClient = new ArrayList<ITodoList>();
			for (TodoList todoList : list) {
				todoListsClient.add(todoList.toTodoListClient());
			}
			return todoListsClient;
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public List<ITodoList> getTodoListsOneSelected(Long selectedTodoListId)
			throws ClientMessageException, ClientUnexpectedException {
		try {
			List<TodoList> list = todoListService.getListForCurrentUser();
			List<ITodoList> todoListsClient = new ArrayList<ITodoList>();
			for (TodoList todoList : list) {
				if (todoList.getId().equals(selectedTodoListId)) {
					todoListsClient.add(todoList.toTodoListClient());
				} else {
					todoListsClient.add(todoList.toTodoListClientBare());
				}
			}
			return todoListsClient;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void updateTodoItem(Long id, String title) {
		try {
			todoItemService.update(id, title);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public void deleteTodoItem(Long id) {
		try {
			todoItemService.deleteById(id);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public Long addTodoItem(Long todoListId, String title) {
		try {
			return todoItemService.addTodoItem(todoListId, title);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public void deleteMultipleTodoItems(List<Long> itemIds) {
		try {
			todoItemService.deleteMultiple(itemIds);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public void moveMultipleTodoItems(List<Long> itemIds, Long destinationTodoListId) {
		try {
			todoItemService.moveMultiple(itemIds, destinationTodoListId);
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public ITodoList addTodoList(String title) {
		try {
			TodoList todoList = todoListService.addTodoList(title);
			return todoList.toTodoListClientBare();
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public void updateTodoListTitle(Long todoListId, String title) {
		try {
			todoListService.update(todoListId, title);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public TwoTodoClientItems moveTodoItemDown(Long id) {
		try {
			TwoTodoItems twoTodoItems = todoItemService.moveDownWithReturn(id);
			if (twoTodoItems != null) {
				return twoTodoItems.toTwoTodoClientItems();
			}
			return null;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public TwoTodoClientItems moveTodoItemUp(Long id) {
		try {
			TwoTodoItems twoTodoItems = todoItemService.moveUpWithReturn(id);
			if (twoTodoItems != null) {
				return twoTodoItems.toTwoTodoClientItems();
			}
			return null;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void deleteTodoList(Long id) {
		try {
			// TODO call delete method which deletes all the items too
			todoListService.deleteById(id);
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public TwoTodoClientLists moveTodoListDown(Long todoListId) throws ClientMessageException,
			ClientUnexpectedException {
		try {
			TwoTodoLists twoTodoLists = todoListService.moveDownWithReturn(todoListId);
			if (twoTodoLists != null) {
				return twoTodoLists.toTwoTodoClientLists();
			}
			return null;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public TwoTodoClientLists moveTodoListUp(Long todoListId) throws ClientMessageException,
			ClientUnexpectedException {
		try {
			TwoTodoLists twoTodoLists = todoListService.moveUpWithReturn(todoListId);
			if (twoTodoLists != null) {
				return twoTodoLists.toTwoTodoClientLists();
			}
			return null;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public List<ITodoItem> addTodoItemsFromTextArea(Long todoListId, String text)
			throws ClientMessageException, ClientUnexpectedException {
		try {
			todoItemService.addTodoItemsFromText(todoListId, text);
			TodoList todoList = todoListService.getByIdWithItems(todoListId);
			List<ITodoItem> todoItems = new ArrayList<ITodoItem>();
			for (TodoItem item : todoList.getTodoItems()) {
				todoItems.add(item.toTodoItemClient());
			}
			return todoItems;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void moveTodoItem(Long todoItemId, Long destinationTodoListId) {
		try {
			todoItemService.move(todoItemId, destinationTodoListId);
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void repositionTodoItemBefore(Long todoItemId, Long beforeTodoItemId) {
		try {
			todoItemService.repositionBefore(todoItemId, beforeTodoItemId);
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void repositionTodoListBefore(Long todoListId, Long beforeTodoListId) {
		try {
			todoListService.repositionBefore(todoListId, beforeTodoListId);
		} catch (Exception e) {
			throw handleException(e);
		}		
	}
	
	public void highlightTodoItem(Long todoItemId, boolean highlight) {
		try {
			todoItemService.setHighlightTodoItem(todoItemId, highlight);
		} catch (Exception e) {
			throw handleException(e);
		}
	}
}
