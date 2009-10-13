package com.myconnector.service;

import java.io.Serializable;
import java.util.List;

import com.myconnector.dao.AbstractDAO;
import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.UserData;
import com.myconnector.domain.interfaces.HasTitle;
import com.myconnector.domain.interfaces.HasUserData;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;

public abstract class GenericServiceImpl<T extends Serializable, KeyType extends Serializable>
		implements GenericService<T, KeyType> {

	abstract protected AbstractDAO<T, KeyType> getGenericDAO();

	protected UserDataDAO userDataDAO;

	public void setUserDataDAO(UserDataDAO userDataDAO) {
		this.userDataDAO = userDataDAO;
	}

	public T getById(KeyType id) {
		return getGenericDAO().load(id);
	}

	public List<T> getList() {
		return getGenericDAO().getList();
	}

	protected UserData getCurrentUser() {
		Long userId = CommonThreadLocal.getUserId();
		UserData currentUserData = userDataDAO.load(userId);
		return currentUserData;
	}

	protected void setCurrentUser(T obj) {
		if (obj instanceof HasUserData) {
			UserData currentUserData = getCurrentUser();
			if (((HasUserData) obj).getUserData() == null) {
				((HasUserData) obj).setUserData(currentUserData);
			} else {
				// Make sure user is set to current user, otherwise throw
				// security exception
				if (!((HasUserData) obj).getUserData().equals(currentUserData)) {
					throw new MessageException("exception.security.invalidUser");
				}
			}
		}
	}

	public KeyType save(T obj) {
		checkForTitle(obj);
		setCurrentUser(obj);
		return getGenericDAO().save(obj);
	}

	public void update(T obj) {
		checkForTitle(obj);
		setCurrentUser(obj);
		getGenericDAO().update(obj);
	}

	public void deleteById(KeyType id) {
		getGenericDAO().deleteById(id);
	}
	
	private void checkForTitle(T obj) {
		if(obj != null && obj instanceof HasTitle) {
			String title = ((HasTitle) obj).getTitle();
			if(title == null || title.length() == 0) {
				throw new MessageException("error.titleRequired");
			}
		}
	}

}
