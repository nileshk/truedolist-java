package com.myconnector.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T extends Serializable, KeyType extends Serializable> {

    public T getById(KeyType id);
    
    public List<T> getList();

    public KeyType save(T obj);

    public void update(T obj);	
	
	public void deleteById(KeyType id);
	
}
