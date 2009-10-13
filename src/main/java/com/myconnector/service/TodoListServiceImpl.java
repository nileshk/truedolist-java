package com.myconnector.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.myconnector.dao.AbstractDAO;
import com.myconnector.dao.TodoListDAO;
import com.myconnector.domain.TodoItem;
import com.myconnector.domain.TodoList;
import com.myconnector.domain.UserData;
import com.myconnector.dto.TwoTodoLists;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;
import com.myconnector.xml.todolists.TodoLists;

public class TodoListServiceImpl extends GenericWithUserServiceImpl<TodoList, Long> implements
		TodoListService, ApplicationContextAware {

	static Logger logger = Logger.getLogger(TodoListServiceImpl.class);

	private TodoListDAO todoListDAO;
	private ApplicationContext applicationContext;

	@Override
	protected AbstractDAO<TodoList, Long> getGenericDAO() {
		return todoListDAO;
	}

	public void setTodoListDAO(TodoListDAO todoListDAO) {
		this.todoListDAO = todoListDAO;
	}
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
		//todoItemService = (TodoItemService) context.getBean("todoItemService");
	}
	
	private TodoItemService getTodoItemService() {
		return (TodoItemService) applicationContext.getBean("todoItemService");
	}
	
	@Override
	public List<TodoList> getListForCurrentUser() {
		List<TodoList> todoLists = super.getListForCurrentUser();
		for (TodoList todoList : todoLists) {
			if (todoList.getTodoItems() != null) {
				todoList.getTodoItems().size();
			}
		}
		return todoLists;
	}

	@Override
	public void deleteById(Long id) {
		TodoList todoList = todoListDAO.load(id);
		assertObjectBelongsToCurrentUser(todoList);
		if (todoList == null) {
			throw new MessageException("todolist.doesnotexist");
		}
		if (todoList.getTodoItems().size() > 0) {
			throw new MessageException("todolist.cantDeleteNotEmpty");
		}
		super.deleteById(id);
	}

	public Long addTodoItem(Long todoListId, String title) {
		// TODO This doesn't set position and doesn't return id
		TodoList todoList = todoListDAO.load(todoListId);
		assertObjectBelongsToCurrentUser(todoList);
		TodoItem todoItem = new TodoItem();
		todoList.addTodoItem(todoItem);
		return null;
	}

	public void update(Long id, String title) {
		TodoList todoList = getById(id);
		todoList.setTitle(title);
		update(todoList);
	}

	@Override
	public Long save(TodoList obj) {
		if (setPositionIfNull(obj)) {
			super.save(obj);
		}
		return null;
	}

	@Override
	public void update(TodoList obj) {
		if (setPositionIfNull(obj)) {
			super.update(obj);
		}
	}

	private boolean setPositionIfNull(TodoList obj) {
		if (obj.getPosition() == null) {
			// Get the next position
			/*
			 * TODO This whole block of code is horribly inefficient and needs to be replaced,
			 * surely we can just do a database query that returns the next position?
			 */
			// XXX What if todo list is null?
			List<TodoList> list = getListForCurrentUser();
			int position = 0;
			TodoList itemToUpdate = null;
			for (TodoList item : list) {
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
				return false;
			}

			obj.setPosition(position);
		}
		return true;
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
		List<TodoList> list = getListForCurrentUser();
		for (int i = 0; i < list.size(); i++) {
			TodoList item = list.get(i);
			if (item.getId().equals(id)) {
				TodoList adjacentItem;
				if (moveUp) {
					if (i == 0) {
						return;
					}
					adjacentItem = list.get(i - 1);
				} else {
					if (i == list.size() - 1) {
						return;
					}
					adjacentItem = list.get(i + 1);
				}
				Integer positionBefore = adjacentItem.getPosition();
				adjacentItem.setPosition(item.getPosition());
				item.setPosition(positionBefore);
			}
		}
	}

	private TwoTodoLists moveUpOrDownWithReturn(Long id, boolean moveUp) {
		List<TodoList> list = getListForCurrentUser();
		TwoTodoLists twoTodoLists = new TwoTodoLists();
		for (int i = 0; i < list.size(); i++) {
			TodoList item = list.get(i);
			if (item.getId().equals(id)) {
				TodoList adjacentItem;
				if (moveUp) {
					if (i == 0) {
						return null;
					}
					adjacentItem = list.get(i - 1);
				} else {
					if (i == list.size() - 1) {
						return null;
					}
					adjacentItem = list.get(i + 1);
				}
				Integer positionBefore = adjacentItem.getPosition();
				adjacentItem.setPosition(item.getPosition());
				item.setPosition(positionBefore);
				twoTodoLists.setTodoList1(adjacentItem);
				twoTodoLists.setTodoList2(item);
				return twoTodoLists;
			}
		}
		return null;
	}

	public TodoList getByIdWithItems(Long id) {
		TodoList list = getById(id);
		if (list != null) {
			list.getTodoItems().size();
		}
		return list;
	}

	public TodoList addTodoList(String title) {
		TodoList todoList = new TodoList();
		todoList.setTitle(title);
		save(todoList);
		return todoList;
	}

	public TwoTodoLists moveDownWithReturn(Long todoListId) {
		return moveUpOrDownWithReturn(todoListId, false);
	}

	public TwoTodoLists moveUpWithReturn(Long todoListId) {
		return moveUpOrDownWithReturn(todoListId, true);
	}

	public void repositionBefore(Long todoListId, Long beforeTodoListId) {
		TodoList todoList = getById(todoListId);
		List<TodoList> list = getListForCurrentUser();
		boolean beforeItemFound = false;
		int positionNumber = 0;
		for (TodoList item : list) {
			if (!item.equals(todoList)) {
				if (beforeItemFound == true) {
					positionNumber++;
					item.setPosition(positionNumber + 1);
					todoListDAO.save(item);
				} else if (item.getId().equals(beforeTodoListId)) {
					todoList.setPosition(item.getPosition().intValue());
					logger.debug("Modifying item to move: Title: " + todoList.getTitle()
							+ ", Position: " + todoList.getPosition());
					todoListDAO.save(todoList);
					beforeItemFound = true;
					positionNumber = item.getPosition().intValue() + 1;
					item.setPosition(positionNumber);
					todoListDAO.save(item);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Title: " + item.getTitle() + ", Position: " + item.getPosition());
			}
		}
		// XXX Should we throw an error if something wasn't found?

	}

	public com.myconnector.xml.todolists.TodoLists getJaxbTodoLists() {
		List<TodoList> todoListList = getListForCurrentUser();
		com.myconnector.xml.todolists.TodoLists todoListsXml = new com.myconnector.xml.todolists.TodoLists();
		for (TodoList todoList : todoListList) {
			com.myconnector.xml.todolists.TodoList todoListXml = toTodoListJaxb(todoList);
			todoListsXml.getTodoList().add(todoListXml);
		}
		return todoListsXml;

	}
	
	public com.myconnector.xml.todolists.TodoLists getJaxbTodoListsComplete() {
		List<TodoList> todoListList = getListForCurrentUser();
		com.myconnector.xml.todolists.TodoLists todoListsXml = new com.myconnector.xml.todolists.TodoLists();
		for (TodoList todoList : todoListList) {
			com.myconnector.xml.todolists.TodoList todoListXml = toTodoListJaxbComplete(todoList);
			todoListsXml.getTodoList().add(todoListXml);
		}
		return todoListsXml;
	}

	private com.myconnector.xml.todolists.TodoList toTodoListJaxb(TodoList todoList) {
		com.myconnector.xml.todolists.TodoList todoListXml = new com.myconnector.xml.todolists.TodoList();
		todoListXml.setId(todoList.getId());
		todoListXml.setTitle(todoList.getTitle());
		return todoListXml;
	}

	private com.myconnector.xml.todolists.TodoList toTodoListJaxbComplete(TodoList todoList) {
		com.myconnector.xml.todolists.TodoList todoListXml = new com.myconnector.xml.todolists.TodoList();
		todoListXml.setId(todoList.getId());
		todoListXml.setTitle(todoList.getTitle());
		for (TodoItem todoItem : todoList.getTodoItems()) {
			com.myconnector.xml.todolists.TodoItem todoItemXml = new com.myconnector.xml.todolists.TodoItem();
			todoItemXml.setId(todoItem.getId());
			todoItemXml.setTitle(todoItem.getTitle());
			todoListXml.getTodoItem().add(todoItemXml);
		}
		return todoListXml;
	}

	public com.myconnector.xml.todolists.TodoList getJaxbTodoList(Long todoListId) {
		return toTodoListJaxb(getByIdForCurrentUser(todoListId));
	}

	public com.myconnector.xml.todolists.TodoList getJaxbTodoListComplete(Long todoListId) {
		return toTodoListJaxbComplete(getByIdForCurrentUser(todoListId));
	}

	public String getXmlForAllLists() {
		com.myconnector.xml.todolists.TodoLists todoListsXml = getJaxbTodoListsComplete();
		try {
			JAXBContext context = JAXBContext
					.newInstance(com.myconnector.xml.todolists.TodoLists.class);
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			// Uncomment the following line if we want human-readable output
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(todoListsXml, new BufferedWriter(stringWriter));
			String output = stringWriter.toString();
			if (logger.isDebugEnabled()) {
				logger.debug(output);
			}
			return output;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String getXmlForList(Long todoListId) {
		throw new UnsupportedOperationException(); // TODO
	}

	public void importJaxbTodoLists(String todoListsXml) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(com.myconnector.xml.todolists.TodoLists.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader stringReader = new StringReader(todoListsXml);
			com.myconnector.xml.todolists.TodoLists todoLists = (TodoLists) unmarshaller
					.unmarshal(new BufferedReader(stringReader));
			for (com.myconnector.xml.todolists.TodoList list : todoLists.getTodoList()) {
				TodoList todoList = addTodoList(list.getTitle());
				logger.debug("Import list: " + todoList.getTitle());
				for (com.myconnector.xml.todolists.TodoItem item : list.getTodoItem()) {
					//addTodoItem(todoList.getId(), item.getTitle());
					TodoItem todoItem = new TodoItem();
					todoItem.setTitle(item.getTitle());
					todoItem.setUserData(new UserData(CommonThreadLocal.getUserId()));
					todoList.addTodoItem(todoItem);
					getTodoItemService().save(todoItem);
					logger.debug("  Import item: " + todoItem.getTitle());
				}
				//save(todoList);
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

}
