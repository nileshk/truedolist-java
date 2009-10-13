package com.myconnector.client.composite;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.CheckBoxChangeListener;
import com.myconnector.client.ClientConstants;
import com.myconnector.client.Confirm;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.listeners.ConfirmListener;
import com.myconnector.client.listeners.EditListener;
import com.myconnector.client.listeners.TodoItemMoveListener;
import com.myconnector.client.model.TodoListsChangeListener;

public class ItemComposite extends BaseTodoComposite implements IItemComposite, ClickListener,
		KeyboardListener, TodoListsChangeListener, HasDragHandle {

	private VerticalPanel mainPanel;
	private String title = "";
	private boolean editMode = false;
	private boolean highlighted = false;
	private Button saveButton;
	private Button deleteButton;
	private Button moveUpButton;
	private Button moveDownButton;
	private Button highlightButton;
	private Image dragHandle;
	private Label titleLabel;
	private TextBox textBox;
	private CheckBox checkBox;
	private List<EditListener> editListeners = new ArrayList<EditListener>();
	private List<CheckBoxChangeListener> checkBoxChangeListeners = new ArrayList<CheckBoxChangeListener>();
	private List<TodoItemMoveListener> todoItemMoveListeners = new ArrayList<TodoItemMoveListener>();
	private ITodoItem todoItem = null;
	private ITodoList todoList = null;
	private TodoImageBundle todoImageBundle;
	private PickupDragController dragController;
	private DropController dropController;
	private HTML insertPreview = new HTML(ClientConstants.HTML_NON_BREAKING_SPACE);

	public void setDragController(PickupDragController dragController) {
		this.dragController = dragController;
		dropController = new AbstractDropController(this) {
			@Override
			public void onDrop(DragContext context) {
				Widget widget = context.draggable;
				if (widget != null && widget instanceof IItemComposite) {
					IItemComposite itemCompositeToMove = (IItemComposite) widget;
					ITodoItem todoItemToMove = itemCompositeToMove.getTodoItem();
					// ITodoList sourceTodoList = itemCompositeToMove.getTodoList();
					// TODO support cross-list moves
					domainModel.repositionTodoItemBefore(todoItemToMove, todoItem, todoList);
				}
			}

			@Override
			public void onPreviewDrop(DragContext context) throws VetoDragException {
				super.onPreviewDrop(context);
				Widget widget = context.draggable;
				if (widget == null || !(widget instanceof IItemComposite)) {
					throw new VetoDragException();
				}
			}

			@Override
			public void onEnter(DragContext context) {
				Widget widget = context.draggable;
				if (widget != null && widget instanceof IItemComposite) {
					// IItemComposite itemCompositeToMove = (IItemComposite) widget;
					// String title = itemCompositeToMove.getTodoItem().getTitle();
					// String spacer = title.replaceAll(".",
					// ClientConstants.HTML_NON_BREAKING_SPACE);
					// insertPreview.setHTML(spacer);
					insertPreview.setVisible(true);
				}
				super.onEnter(context);
			}

			@Override
			public void onLeave(DragContext context) {
				insertPreview.setVisible(false);
				super.onLeave(context);
			}
		};
		this.dragController.registerDropController(dropController);
	}

	public ItemComposite(ITodoList todoList, ITodoItem todoItem, TodoImageBundle todoImageBundle) {
		this.todoList = todoList;
		this.todoItem = todoItem;
		this.title = todoItem.getTitle();
		if (todoItem.getHighlighted() != null && todoItem.getHighlighted()) {
			this.highlighted = true;
		}
		this.todoImageBundle = todoImageBundle;
	}

	public void destroy() {
		super.destroy();
		dragController.unregisterDropController(dropController);
	}

	public void init() {
		mainPanel = new VerticalPanel();
		final HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(4);
		hPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		checkBox = new CheckBox();
		checkBox.addClickListener(this);
		hPanel.add(checkBox);
		dragHandle = todoImageBundle.todo_item_icon().createImage();
		hPanel.add(dragHandle);
		titleLabel = new Label(title);
		titleLabel.addStyleName(ClientConstants.CSS_TODO_ITEM_LABEL);
		hPanel.add(titleLabel);

		textBox = new TextBox();
		textBox.setVisibleLength(50);
		textBox.setVisible(false);
		textBox.addKeyboardListener(this);
		hPanel.add(textBox);

		saveButton = new Button(textConstants.save());
		saveButton.setVisible(false);
		hPanel.add(saveButton);

		deleteButton = new Button(textConstants.delete());
		deleteButton.setVisible(false);
		hPanel.add(deleteButton);

		moveUpButton = new Button(textConstants.moveUp());
		moveUpButton.setVisible(false);
		hPanel.add(moveUpButton);

		moveDownButton = new Button(textConstants.moveDown());
		moveDownButton.setVisible(false);
		hPanel.add(moveDownButton);
		
		highlightButton = new Button(textConstants.highlight());
		highlightButton.setVisible(false);
		hPanel.add(highlightButton);

		titleLabel.addMouseListener(new MouseListener() {

			public void onMouseDown(Widget sender, int x, int y) {
			}

			public void onMouseEnter(Widget sender) {
			}

			public void onMouseLeave(Widget sender) {
			}

			public void onMouseMove(Widget sender, int x, int y) {
			}

			public void onMouseUp(Widget sender, int x, int y) {
				if (!editMode) {
					switchToEditMode();
				}
			}
		});

		saveButton.addClickListener(this);
		deleteButton.addClickListener(this);
		moveUpButton.addClickListener(this);
		moveDownButton.addClickListener(this);
		highlightButton.addClickListener(this);

		insertPreview.setVisible(false);
		insertPreview.addStyleName(ClientConstants.CSS_DRAGDROP_POSITIONER);
		mainPanel.add(insertPreview);
		mainPanel.add(hPanel);

		if (highlighted) {
			titleLabel.addStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
			textBox.addStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
		}
		
		initWidget(mainPanel);
	}

	public Long getId() {
		return todoItem.getId();
	}

	public ITodoItem getTodoItem() {
		return todoItem;
	}

	public ITodoList getTodoList() {
		return todoList;
	}

	public void onClick(Widget sender) {
		if (sender == saveButton) {
			saveEdit();
		} else if (sender == deleteButton) {
			delete();
		} else if (sender == checkBox) {
			checkBoxChange();
		} else if (sender == moveUpButton) {
			moveUp();
		} else if (sender == moveDownButton) {
			moveDown();
		} else if (sender == highlightButton) {
			highlight();
		}
	}

	public boolean isChecked() {
		return checkBox.isChecked();
	}

	public void addCheckBoxChangeListener(CheckBoxChangeListener listener) {
		checkBoxChangeListeners.add(listener);
	}

	private void checkBoxChange() {
		for (CheckBoxChangeListener listener : checkBoxChangeListeners) {
			listener.onCheckBoxChange(this, checkBox.isChecked());
		}

	}

	public void edit() {
		switchToEditMode();
	}

	private void switchToEditMode() {
		titleLabel.setVisible(false);
		textBox.setText(titleLabel.getText());
		textBox.setVisible(true);
		saveButton.setVisible(true);
		deleteButton.setVisible(true);
		moveUpButton.setVisible(true);
		moveDownButton.setVisible(true);
		highlightButton.setVisible(true);
		editMode = true;
		for (EditListener listener : editListeners) {
			listener.onEdit(this);
		}
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				textBox.setFocus(true);
			}
		});

	}

	private void saveEdit() {
		disableEdit();
		domainModel.updateTodoItemTitle(this, todoItem, textBox.getText());
	}

	public void addEditListener(EditListener listener) {
		editListeners.add(listener);
	}

	public void disableEdit() {
		textBox.setVisible(false);
		saveButton.setVisible(false);
		deleteButton.setVisible(false);
		moveUpButton.setVisible(false);
		moveDownButton.setVisible(false);
		highlightButton.setVisible(false);
		titleLabel.setVisible(true);
		editMode = false;
	}

	private void delete() {
		Confirm.confirm(textConstants.deleteConfirmation(), deleteConfirmListener, textConstants
				.delete(), textConstants.cancel());
	}

	private ConfirmListener deleteConfirmListener = new ConfirmListener() {
		public void onNo(Widget sender) {
		}

		public void onYes(Widget sender) {
			domainModel.deleteTodoItem(ItemComposite.this, todoItem);
		}
	};

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		if (sender == textBox) {
			if (keyCode == KEY_ENTER) {
				saveEdit();
			} else if (keyCode == KEY_ESCAPE) {
				disableEdit();
			}
		}
	}

	public void addTodoItemMoveListener(TodoItemMoveListener listener) {
		todoItemMoveListeners.add(listener);
	}

	private void moveUp() {
		domainModel.moveTodoItemUp(this, todoList, todoItem);
	}

	private void moveDown() {
		domainModel.moveTodoItemDown(this, todoList, todoItem);
	}

	public Widget asWidget() {
		return this;
	}

	public Widget getDragHandle() {
		return dragHandle;
	}

	@Override
	public void onTodoItemDeleted(Object originator, ITodoItem todoItem) {
		super.onTodoItemDeleted(originator, todoItem);
		// TODO remove event listeners?
	}

	@Override
	public void onTodoItemModified(Object originator, ITodoItem todoItemModified) {
		super.onTodoItemModified(originator, todoItemModified);
		// XXX
		if (todoItem.getId().equals(todoItemModified.getId())) {
			titleLabel.setText(todoItemModified.getTitle());
			if (todoItemModified.getHighlighted() != null && todoItemModified.getHighlighted()) {
				highlighted = true;
				titleLabel.addStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
				textBox.addStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
			} else {
				highlighted = false;
				titleLabel.removeStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
				textBox.removeStyleName(ClientConstants.CSS_TODO_ITEM_HIGHLIGHTED);
			}
			for (EditListener listener : editListeners) {
				listener.onFinishedEdit(this);
			}
		}
	}

	private void highlight() {
		boolean highlight = false;
		if (todoItem.getHighlighted() == null || todoItem.getHighlighted().booleanValue() == false) {
			highlight = true;
		}
		domainModel.setHighlightTodoItem(this, todoItem, highlight);
	}

}
