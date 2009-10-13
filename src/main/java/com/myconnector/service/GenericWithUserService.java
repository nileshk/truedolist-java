package com.myconnector.service;

import java.io.Serializable;
import java.util.List;

public interface GenericWithUserService<T extends Serializable, KeyType extends Serializable> extends GenericService<T, KeyType> {

	public List<T> getListForCurrentUser();
	
	/**
	 * Get object by id only if it belongs to
	 * 
	 * @param id
	 * @return
	 */
	public T getByIdForCurrentUser(KeyType id);
	
}
