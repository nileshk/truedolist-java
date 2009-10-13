package com.myconnector.client.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.listeners.OkCancelListener;
import com.myconnector.client.model.TodoListsChangeListener;

public class ListPanelComposite extends BaseTodoComposite implements IListPanelComposite,
		ClickListener, TodoListsChangeListener {

	private ITodoList selectedTodoList = null;
	private VerticalPanel listsPanel;
	private Button addNewListButton;
	private List<ITodoList> todoLists;
	private List<ITodoListComposite> todoListComposites = new ArrayList<ITodoListComposite>();

	/**
	 * Method must be overridden
	 * 
	 * @param todoList
	 * @return
	 */
	protected ITodoListComposite createTodoListComposite(ITodoList todoList) {
		throw new UnsupportedOperationException();
	}

	public void destroy() {
		super.destroy();
		destroyChildObjects();
	}

	private void destroyChildObjects() {
		for (ITodoListComposite todoListComposite : todoListComposites) {
			todoListComposite.destroy();
		}
	}

	public ListPanelComposite(List<ITodoList> todoLists) {
		this.todoLists = todoLists;
	}
	
	public void init() {
		final VerticalPanel mainPanel = new VerticalPanel();
		listsPanel = new VerticalPanel();
		listsPanel.setSpacing(7);
		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.setSpacing(7);
		addNewListButton = new Button(textConstants.plusNewList());
		addNewListButton.addClickListener(this);
		buttonPanel.add(addNewListButton);

		mainPanel.add(buttonPanel);
		mainPanel.add(listsPanel);

		addTodoListComposites();

		initWidget(mainPanel);
	}

	private ITodoListComposite getSelectedTodoListComposite() {
		if (selectedTodoList != null) {
			Iterator<Widget> iterator = listsPanel.iterator();
			while (iterator.hasNext()) {
				Widget widget = (Widget) iterator.next();
				if (widget instanceof ITodoListComposite) {
					ITodoListComposite todoListComposite = (ITodoListComposite) widget;
					ITodoList todoList = todoListComposite.getTodoList();
					// XXX why doesn't object comparison work here?
					if (todoList != null && todoList.getId().equals(selectedTodoList.getId())) {
						return todoListComposite;
					}
				}
			}
		}
		return null;
	}

	private void addTodoListComposites() {
		ITodoList todoListSelected = null;
		if (getSelectedTodoListComposite() != null) {
			todoListSelected = getSelectedTodoListComposite().getTodoList();
		}
		for (ITodoList todoList : todoLists) {
			ITodoListComposite todoListComposite = createTodoListComposite(todoList);
			if (todoListSelected != null && todoListSelected == todoList) {
				todoListComposite.select();
			}
			addTodoListComposite(todoListComposite);
		}
	}

	public void refresh() {
		destroyChildObjects();
		listsPanel.clear();
		todoListComposites.clear();
		addTodoListComposites();
		ITodoListComposite selectedTodoListComposite = getSelectedTodoListComposite();
		if (selectedTodoListComposite != null) {
			selectedTodoListComposite.select();
		}
	}

	private void addTodoListComposite(ITodoListComposite todoListComposite) {
		// TODO keep TodoListComposites in their own list, then render listsPanel from the list
		todoListComposites.add(todoListComposite);
		listsPanel.add(todoListComposite.asWidget());
	}

	public void onClick(Widget sender) {
		if (sender == addNewListButton) {
			startAddNew();
		}
	}

	private void startAddNew() {
		final EditTitleComposite editTitleComposite = new EditTitleComposite();
		editTitleComposite.addOkCancelListener(new OkCancelListener() {
			public void onCancel(Widget sender) {
				listsPanel.remove(editTitleComposite);
			}

			public void onOk(Widget sender) {
				String title = editTitleComposite.getText();
				listsPanel.remove(editTitleComposite);
				addNewList(title);
			}
		});
		listsPanel.add(editTitleComposite);

	}

	private void addNewList(String title) {
		domainModel.addTodoList(this, title);
	}

	public Widget asWidget() {
		return this;
	}

	public void onTodoListSelected(Object originator, ITodoList todoList) {
		if (getSelectedTodoListComposite() != originator) {
			if (getSelectedTodoListComposite() != null) {
				getSelectedTodoListComposite().unselect();
			}
			selectedTodoList = todoList;
		}
	}

	public void onTodoListDeleted(Object originator, ITodoList todoListToDelete) {
		Iterator<Widget> iterator = listsPanel.iterator();
		while (iterator.hasNext()) {
			Widget widget = (Widget) iterator.next();
			if (widget instanceof ITodoListComposite) {
				ITodoListComposite todoListComposite = (ITodoListComposite) widget;
				ITodoList todoList = todoListComposite.getTodoList();
				GWT.log(todoList.getTitle() + ":" + todoListToDelete.getTitle(), null);
				// XXX Why didn't an object comparison work here?
				if (todoList != null && todoList.getId().equals(todoListToDelete.getId())) {
					listsPanel.remove(widget);
					todoListComposites.remove(todoListComposite);
				}
			}
		}
	}

	@Override
	public void onTodoListAdded(Object originator, ITodoList todoList) {
		super.onTodoListAdded(originator, todoList);
		addTodoListComposite(createTodoListComposite(todoList));
	}

}
