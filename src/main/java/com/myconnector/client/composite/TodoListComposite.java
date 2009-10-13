package com.myconnector.client.composite;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.ClientConstants;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;

public class TodoListComposite extends BaseTodoComposite implements ITodoListComposite,
		ClickListener, HasDragHandle {

	private Label label;
	private ITodoList todoList = null;
	private PickupDragController dragController; // TODO make this an interface somehow
	private DropController dropController;
	private TodoImageBundle todoImageBundle;
	private Image dragHandle;
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HTML insertPreview = new HTML(ClientConstants.HTML_NON_BREAKING_SPACE);

	public void setDragController(PickupDragController dragController) {
		this.dragController = dragController;
		dropController = new AbstractDropController(this) {
			@Override
			public void onDrop(DragContext context) {
				Widget widget = context.draggable;
				if (widget != null) {
					if (widget instanceof IItemComposite) {
						IItemComposite itemCompositeToMove = (IItemComposite) widget;
						ITodoItem todoItemToMove = itemCompositeToMove.getTodoItem();
						ITodoList sourceTodoList = itemCompositeToMove.getTodoList();
						domainModel.moveTodoItem(sourceTodoList, todoList, todoItemToMove);
					} else if (widget instanceof ITodoListComposite) {
						ITodoListComposite todoListCompositeToMove = (ITodoListComposite) widget;
						ITodoList todoListToMove = todoListCompositeToMove.getTodoList();
						domainModel.repositionTodoListBefore(todoListToMove, todoList);
					}
				}
			}

			@Override
			public void onPreviewDrop(DragContext context) throws VetoDragException {
				super.onPreviewDrop(context);
				Widget widget = context.draggable;
				if (widget == null
						|| !(widget instanceof IItemComposite || widget instanceof ITodoListComposite)) {
					throw new VetoDragException();
				}
			}

			@Override
			public void onEnter(DragContext context) {
				Widget widget = context.draggable;
				if (widget != null) {
					if (widget instanceof ITodoListComposite) {
						insertPreview.setVisible(true);
					} else if (widget instanceof IItemComposite) {
						addStyleName(ClientConstants.CSS_TODOLIST_PREVIEW_DROP_ITEM);
					}
				}
				super.onEnter(context);
			}

			@Override
			public void onLeave(DragContext context) {
				insertPreview.setVisible(false);
				removeStyleName(ClientConstants.CSS_TODOLIST_PREVIEW_DROP_ITEM);
				super.onLeave(context);
			}
		};
		dragController.registerDropController(dropController);
	}

	public ITodoList getTodoList() {
		return todoList;
	}

	public TodoListComposite(ITodoList todoList, TodoImageBundle todoImageBundle) {
		this.todoList = todoList;
		this.todoImageBundle = todoImageBundle;
		hPanel.setSpacing(4);
		hPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);

		dragHandle = this.todoImageBundle.todo_item_icon().createImage();

		label = new Label(todoList.getTitle());
		label.addStyleName(ClientConstants.CSS_TODO_LIST_LABEL);
		label.addClickListener(this);

		hPanel.add(dragHandle);
		hPanel.add(label);

		insertPreview.setVisible(false);
		insertPreview.addStyleName(ClientConstants.CSS_DRAGDROP_POSITIONER);
		mainPanel.add(insertPreview);
		mainPanel.add(hPanel);
		initWidget(mainPanel);
	}

	public void destroy() {
		GWT.log("Destroying TodoListComposite: todoList.id = " + todoList.getId()
				+ ", todoList.title = " + todoList.getTitle(), null);
		super.destroy();
		dragController.unregisterDropController(dropController);
		GWT.log("Done destroying TodoListComposite: todoList.id = " + todoList.getId()
				+ ", todoList.title = " + todoList.getTitle(), null);
	}

	public void refresh() {
		label.setText(todoList.getTitle());
	}

	public void onClick(Widget sender) {
		select();
		domainModel.selectedTodoList(todoList, this);
	}

	public void unselect() {
		hPanel.setStylePrimaryName("todoListLabel");
	}

	public void select() {
		hPanel.setStylePrimaryName("todoListLabelSelected");
	}

	public Widget asWidget() {
		return this;
	}

	public Widget getDragHandle() {
		return dragHandle;
	}

	@Override
	public void onTodoListTitleUpdated(Object sender, ITodoList todoList, String title) {
		super.onTodoListTitleUpdated(sender, todoList, title);
		if (this.todoList == todoList) {
			refresh();
		}
	}
	
	@Override
	public void onTodoListSelected(Object originator, ITodoList todoList) {
		super.onTodoListSelected(originator, todoList);
		if(this.todoList == todoList) {
			select();
		}
	}

}
