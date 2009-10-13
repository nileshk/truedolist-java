package com.myconnector.dao;

import java.util.List;

public interface AbstractDAO <DomainObject, KeyType> {

    public DomainObject load(KeyType id);
    
    public void update(DomainObject object);

    public KeyType save(DomainObject object);

    public void delete(DomainObject object);
    
    public void deleteById(KeyType id);

    public List<DomainObject> getList();
    
    public void deleteAll();
    
    public int count();
	
}
