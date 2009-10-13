package com.myconnector.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.myconnector.client.domain.TodoListClient;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.domain.interfaces.HasContext;
import com.myconnector.domain.interfaces.HasPosition;
import com.myconnector.domain.interfaces.HasTitle;
import com.myconnector.domain.interfaces.HasUserData;
import com.myconnector.dto.TodoListDTO;
import com.myconnector.dto.TodoListSimpleDTO;

public class TodoList implements Serializable, HasUserData, HasPosition, HasTitle, HasContext,
		TodoListSimpleDTO, TodoListDTO {

    private Long id;
    private String title;
    private UserData userData;
    private Set<TodoItem> todoItems;
    private Integer position;
    private TodoContext context;

    private static final long serialVersionUID = 1L;
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    protected int hashCode = Integer.MIN_VALUE;

    public TodoList() {
        super();
    }

    public TodoList(Long id) {
        this.setId(id);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof TodoList))
            return false;
        else {
            TodoList todo = (TodoList) obj;
            if (null == this.getId() || null == todo.getId())
                return false;
            else
                return (this.getId().equals(todo.getId()));
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = Integer.MIN_VALUE;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(Set<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public void addTodoItem(TodoItem todoItem) {
    	if(todoItems == null) {
    		todoItems = new HashSet<TodoItem>();
    	}
        todoItems.add(todoItem);
        todoItem.setTodoList(this);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
	public TodoContext getContext() {
		return context;
	}

	public void setContext(TodoContext todoContext) {
		this.context = todoContext;
	}

	public TodoListClient toTodoListClientBare() {
        TodoListClient t = new TodoListClient();
        t.setId(getId());
        t.setPosition(getPosition());
        t.setTitle(getTitle());
        return t;
    }

    public TodoListClient toTodoListClient() {
        TodoListClient t = new TodoListClient();
        t.setId(getId());
        t.setPosition(getPosition());
        t.setTitle(getTitle());
        TodoContext tContext = getContext();
		if (tContext != null) {
			t.setContext(tContext.toTodoContextClient());
		}
        List<ITodoItem> todoItemsClient = new ArrayList<ITodoItem>();
        for (TodoItem todoItem : todoItems) {
            todoItemsClient.add(todoItem.toTodoItemClient());
        }
        t.setTodoItems(todoItemsClient);
        t.setTodoItemsLoaded(true);
        return t;
    }

	public static TodoList fromJaxb(com.myconnector.xml.todolists.TodoList jaxb) {
		TodoList newTodoList = new TodoList();
		newTodoList.setId(jaxb.getId());
		newTodoList.setTitle(jaxb.getTitle());
		return newTodoList;
	}

}