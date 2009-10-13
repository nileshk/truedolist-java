package com.myconnector.dao.hibernate;

import com.myconnector.dao.TodoItemDAO;
import com.myconnector.domain.TodoItem;

public class TodoItemDAOImpl extends GenericHibernateWithUserDAOImpl<TodoItem, Long> implements TodoItemDAO {

	@Override
	protected Class<TodoItem> getDomainClass() {
		return TodoItem.class;
	}

}
