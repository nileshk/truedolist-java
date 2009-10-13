package com.myconnector.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.myconnector.dao.TodoListDAO;
import com.myconnector.domain.TodoList;

public class TodoListDAOImpl extends GenericHibernateWithUserDAOImpl<TodoList, Long> implements TodoListDAO {

	@Override
	protected Class<TodoList> getDomainClass() {
		return TodoList.class;
	}
	
	@Override
	protected void criteriaForGetListForUser(Criteria criteria) {
	    criteria.addOrder(Order.asc(TodoList.POSITION));
	    criteria.addOrder(Order.asc(TodoList.TITLE));
	}
}
