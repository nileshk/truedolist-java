package com.myconnector.client.service;

public interface IServiceFactory {

	TodoServiceGwtAsync getTodoService();
	UserServiceGwtAsync getUserService();
}
