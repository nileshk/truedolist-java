package com.myconnector.client.composite;

import com.myconnector.client.AsWidget;
import com.myconnector.client.Refreshable;
import com.myconnector.client.listeners.RequestMoveListener;

public interface IItemListComposite extends AsWidget, Refreshable, IDestroyable {

	void removeFromParent();

	void addRequestMoveListener(RequestMoveListener listener);

	Long getTodoListId();

}
