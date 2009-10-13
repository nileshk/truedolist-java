package com.myconnector.dao;

import com.myconnector.domain.TodoItem;

public class TodoItemDAOTests extends BaseDAOTests {

    TodoItemDAO todoItemDAO;
    
    public void setTodoItemDAO(TodoItemDAO todoItemDAO) {
        this.todoItemDAO = todoItemDAO;
    }
    
    public void testSave() {        
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("test");
        Long id = todoItemDAO.save(todoItem);
        assertNotNull(id);
        print(id.toString());
    }
    
}
