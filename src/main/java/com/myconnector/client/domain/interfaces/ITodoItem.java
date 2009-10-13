package com.myconnector.client.domain.interfaces;

public interface ITodoItem extends HasId, HasTitle, HasPosition {

	Boolean getHighlighted();

	void setHighlighted(Boolean highlighted);
	
}
