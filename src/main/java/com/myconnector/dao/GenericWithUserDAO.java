package com.myconnector.dao;

import java.util.List;

public interface GenericWithUserDAO <DomainObject, KeyType> extends AbstractDAO<DomainObject, KeyType> {

	public List<DomainObject> getListForUser(KeyType userId);
}
