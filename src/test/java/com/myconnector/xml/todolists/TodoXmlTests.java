package com.myconnector.xml.todolists;

import java.io.BufferedWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import junit.framework.TestCase;

public class TodoXmlTests extends TestCase {

	public void testCreateLists() throws JAXBException {
		TodoLists todoLists = (new ObjectFactory()).createTodoLists();

		TodoList todoList1 = (new ObjectFactory()).createTodoList();
		todoList1.setId(Long.valueOf(1));
		todoList1.setTitle("List #1");
		todoList1.setHref("http://truedolist.com/api/todo/lists/1");

		TodoItem todoItem1 = (new ObjectFactory()).createTodoItem();
		todoItem1.setId(Long.valueOf(10));
		todoItem1.setTitle("Item #1");
		todoItem1.setHref("http://truedolist.com/api/todo/items/10");
		// todoList1.getTodoItem().add(todoItem1);

		todoLists.getTodoList().add(todoList1);

		JAXBContext context = JAXBContext.newInstance(TodoLists.class);
		Marshaller marshaller = context.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(todoLists, new BufferedWriter(stringWriter));
		String output = stringWriter.toString();
		System.out.println(output);
		assertTrue(output
				.contains("<todoLists><todoList href=\"http://truedolist.com/api/todo/lists/1\" title=\"List #1\" id=\"1\"/></todoLists>"));
		// assertTrue(output.contains("<todoLists><todoList title=\"List #1\" id=\"1\">"
		// + "<todoItem title=\"Item #1\" id=\"10\"/></todoList></todoLists>"));
	}
}
