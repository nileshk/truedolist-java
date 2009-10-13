package com.myconnector.service;

import java.io.Serializable;
import java.util.Collection;

public interface GenericChildService<T extends Serializable, KeyType> {

	/**
	 * Get list based on a parent id
	 * @param parentId
	 * @return
	 */
	public Collection<T> getList(KeyType parentId);
	
}
