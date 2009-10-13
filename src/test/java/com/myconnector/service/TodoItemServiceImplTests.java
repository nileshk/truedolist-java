package com.myconnector.service;

import com.myconnector.dao.TodoItemDAO;
import com.myconnector.dao.TodoListDAO;
import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.TodoList;
import com.myconnector.domain.UserData;

import static org.easymock.EasyMock.*;

import junit.framework.TestCase;

public class TodoItemServiceImplTests extends TestCase {

	TodoItemServiceImpl service;
	TodoItemDAO todoItemDAO;
	TodoListDAO todoListDAO;
	UserDataDAO userDataDAO;
	TodoListService todoListService;
	
	@Override
	protected void setUp() throws Exception {
		todoItemDAO = createMock(TodoItemDAO.class);
		todoListDAO = createMock(TodoListDAO.class);
		userDataDAO = createMock(UserDataDAO.class);
		todoListService = createMock(TodoListService.class);
		service = new TodoItemServiceImpl();
		service.setTodoItemDAO(todoItemDAO);
		service.setTodoListDAO(todoListDAO);
		service.setUserDataDAO(userDataDAO);
		// TODO setup HttpSessionThreadLocal with a mock login
	}
	
	private void replayAll() {
		replay(todoItemDAO, todoListDAO, userDataDAO);
	}
	
	private void verifyAll() {
		verify(todoItemDAO, todoListDAO, userDataDAO);
	}
	
	public void testAddTodoItemsFromTextList() {
		Long todoListId = Long.valueOf(234);
		String text = "test1\n\rtest2\r\ntest3\ntest4\rtest5\ntest6";
		TodoList todoList = new TodoList();
		UserData userData = new UserData();
		userData.setId(Long.valueOf(123));
		todoList.setUserData(userData);
		expect(todoListDAO.load(todoListId)).andReturn(todoList);
		expect(userDataDAO.load(null)).andReturn(null);
		expect(todoListService.save(todoList)).andReturn(Long.valueOf(234));		
		replayAll();
		service.addTodoItemsFromText(todoListId, text);
		verifyAll();
	}
	
}
