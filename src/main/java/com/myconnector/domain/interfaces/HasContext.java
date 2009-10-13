package com.myconnector.domain.interfaces;

import com.myconnector.domain.TodoContext;

public interface HasContext {

	public TodoContext getContext();

	public void setContext(TodoContext context);
	
}
