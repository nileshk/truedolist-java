package com.myconnector.service;

import java.io.Serializable;
import java.util.List;

import com.myconnector.dao.GenericWithUserDAO;
import com.myconnector.domain.UserData;
import com.myconnector.domain.interfaces.HasUserData;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;

public abstract class GenericWithUserServiceImpl<T extends Serializable, KeyType extends Serializable>
		extends GenericServiceImpl<T, KeyType> implements
		GenericWithUserService<T, KeyType> {

	@Override
	public T getById(KeyType id) {
		T obj = super.getById(id);	
		assertObjectBelongsToCurrentUser(obj);
		return obj;
	}
	
	public List<T> getListForCurrentUser() {
		if (getGenericDAO() instanceof GenericWithUserDAO) {
			Long userId = CommonThreadLocal.getUserId();
			GenericWithUserDAO dao = (GenericWithUserDAO) getGenericDAO();
			return dao.getListForUser(userId);
		} else {
			throw new MessageException("exception.method.userNotSupported");
		}
	}

	protected void assertObjectBelongsToCurrentUser(Object obj) {
		if (obj instanceof HasUserData) {
			Long userId = CommonThreadLocal.getUserId();
			HasUserData hasUserData = (HasUserData) obj;
			Long objUserId = hasUserData.getUserData().getId();
			if(!userId.equals(objUserId)) {
				throw new MessageException("exception.security.invalidUser");
			}					
		} else {
			throw new MessageException("exception.notHasUserDataObject");
		}
	}
	
	public T getByIdForCurrentUser(KeyType id) {
		T obj = getById(id);
		assertObjectBelongsToCurrentUser(obj);
		return obj;
	}
}
