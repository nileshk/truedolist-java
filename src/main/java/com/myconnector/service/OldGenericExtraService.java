package com.myconnector.service;

/**
 * More generic methods. These involve Object types.
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface OldGenericExtraService extends OldGenericService {

    public Object getById(String id);

    public void save(Object obj);

    public void update(Object obj);

}