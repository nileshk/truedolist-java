package com.myconnector.client.composite;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.myconnector.client.CheckBoxChangeListener;
import com.myconnector.client.ClientConstants;
import com.myconnector.client.Confirm;
import com.myconnector.client.ExceptionHandler;
import com.myconnector.client.domain.TodoItemClient;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.listeners.ConfirmListener;
import com.myconnector.client.listeners.EditListener;
import com.myconnector.client.listeners.OkCancelListener;
import com.myconnector.client.listeners.RequestDeleteListener;
import com.myconnector.client.listeners.RequestImportTextListener;
import com.myconnector.client.listeners.RequestMoveListener;
import com.myconnector.client.model.TodoListsChangeListener;

public class ItemListComposite extends BaseTodoComposite implements IItemListComposite,
		ClickListener, EditListener, KeyboardListener, FocusListener, CheckBoxChangeListener,
		RequestDeleteListener, RequestMoveListener, TodoListsChangeListener, IDestroyable,
		RequestImportTextListener {

	private Button addItemButton;
	private Button deleteMultiButton;
	private Button moveMultiButton;
	private TextBox addItemTextBox;
	private ListBox moveListBox;
	private TextArea importTextArea;
	private Button importTextButton;
	private Button cancelImportTextButton;
	private VerticalPanel itemListPanel;
	private HorizontalPanel multiPanel;
	private VerticalPanel importTextPanel;
	private Label titleLabel;
	private EditTitleComposite editTitleComposite;
	private List<IItemComposite> itemComposites = new ArrayList<IItemComposite>();
	private ITodoList todoList = null;
	private List<ITodoList> todoLists = null;
	private List<IItemComposite> checkedItemComposites = new ArrayList<IItemComposite>();
	private List<RequestMoveListener> requestMoveListeners = new ArrayList<RequestMoveListener>();

	protected IItemComposite getItemComposite(ITodoList todoList, ITodoItem todoItem) {
		throw new UnsupportedOperationException();
	}

	public ItemListComposite(ITodoList todoList, List<ITodoList> todoLists) {
		this.todoList = todoList;
		this.todoLists = todoLists;
	}

	public void destroy() {
		super.destroy();
		destroyChildObjects();
	}

	private void destroyChildObjects() {
		for (IItemComposite itemComposite : itemComposites) {
			itemComposite.destroy();
		}
	}

	public void init() {
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setSpacing(5);
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.setSpacing(5);
		titleLabel = new Label(todoList.getTitle());
		titleLabel.addStyleName(ClientConstants.CSS_TODO_LIST_HEADER_LABEL);
		editTitleComposite = new EditTitleComposite();
		editTitleComposite.setVisible(false);
		editTitleComposite.addOkCancelListener(editTitleOkCancelListener);
		editTitleComposite.addRequestDeleteListener(this);
		editTitleComposite.addRequestMoveListener(this);
		editTitleComposite.addRequestImportTextListeners(this);
		titleLabel.addClickListener(this);
		titlePanel.add(titleLabel);
		titlePanel.add(editTitleComposite);
		itemListPanel = new VerticalPanel();
		itemListPanel.setSpacing(5);
		if (todoList.isTodoItemsLoaded()) {
			processTodoItems();
		} else {
			updateTodoList(todoList);
		}

		HorizontalPanel addItemPanel = new HorizontalPanel();
		addItemTextBox = new TextBox();
		addItemTextBox.setVisibleLength(50);
		addItemTextBox.addKeyboardListener(this);
		addItemTextBox.addFocusListener(this);
		addItemButton = new Button(textConstants.addItem());
		addItemButton.addClickListener(this);
		addItemButton.setEnabled(false);
		addItemPanel.add(addItemTextBox);
		addItemPanel.add(addItemButton);

		multiPanel = new HorizontalPanel();
		deleteMultiButton = new Button(textConstants.deleteCheckedItems());
		deleteMultiButton.addClickListener(this);
		multiPanel.add(deleteMultiButton);

		moveListBox = new ListBox();
		moveListBox.setVisibleItemCount(1);
		multiPanel.add(new Label(textConstants.moveCheckedItemsTo()));
		multiPanel.add(moveListBox);
		moveMultiButton = new Button(textConstants.moveToList());
		moveMultiButton.addClickListener(this);
		multiPanel.add(moveMultiButton);
		multiPanel.setVisible(false);
		multiPanel.setSpacing(5);

		importTextPanel = new VerticalPanel();
		importTextArea = new TextArea();
		importTextArea.setSize("50em", "25em");
		importTextButton = new Button("Import");
		importTextButton.addClickListener(this);
		cancelImportTextButton = new Button(textConstants.cancel());
		cancelImportTextButton.addClickListener(this);
		importTextPanel.add(importTextArea);
		HorizontalPanel importTextButtonPanel = new HorizontalPanel();
		importTextButtonPanel.add(importTextButton);
		importTextButtonPanel.add(cancelImportTextButton);
		importTextPanel.add(importTextButtonPanel);

		importTextPanel.setVisible(false);

		mainPanel.add(titlePanel);
		mainPanel.add(importTextPanel);
		mainPanel.add(itemListPanel);
		mainPanel.add(multiPanel);
		mainPanel.add(addItemPanel);

		initWidget(mainPanel);

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				addItemTextBox.setFocus(true);
			}
		});
	}

	private void reloadMoveListBox(ITodoList todoList, List<ITodoList> todoLists) {
		moveListBox.clear();
		for (ITodoList list : todoLists) {
			if (list != todoList) {
				moveListBox.addItem(list.getTitle(), list.getId().toString());
			}
		}
	}

	AsyncCallback<List<ITodoItem>> getTodoItemsForListCallback = new AsyncCallback<List<ITodoItem>>() {
		public void onFailure(Throwable caught) {
			ExceptionHandler.handle(caught);
		}

		public void onSuccess(List<ITodoItem> result) {
			todoList.setTodoItems(result);
			todoList.setTodoItemsLoaded(true);
			processTodoItems();
		}
	};

	private void updateTodoList(final ITodoList todoList) {
		if (!todoList.isTodoItemsLoaded()) {
			domainModel.fetchTodoItemsForList(this, todoList);
		} else {
			processTodoItems();
		}
	}

	public void refresh() {
		importTextPanel.setVisible(false);
		importTextArea.setText("");
		destroyChildObjects();
		itemListPanel.clear();
		processTodoItems();
	}

	private void refreshAndEdit(ITodoItem todoItemToEdit) {
		itemListPanel.clear();
		for (ITodoItem todoItem : todoList.getTodoItems()) {
			IItemComposite itemComposite = addItemComposite(todoItem);
			// XXX
			if (todoItem.getId().equals(todoItemToEdit.getId())) {
				itemComposite.edit();
			}
		}
	}

	private void processTodoItems() {
		for (ITodoItem todoItem : todoList.getTodoItems()) {
			addItemComposite(todoItem);
		}
	}

	public void onClick(Widget sender) {
		if (sender == addItemButton) {
			addItem();
		} else if (sender == deleteMultiButton) {
			deleteMulti();
		} else if (sender == moveMultiButton) {
			moveMulti();
		} else if (sender == titleLabel) {
			startTitleEdit();
		} else if (sender == importTextButton) {
			importText();
		} else if (sender == cancelImportTextButton) {
			cancelImport();
		}
	}

	private void addItem() {
		final ITodoItem todoItem = new TodoItemClient();
		String title = addItemTextBox.getText();
		todoItem.setTitle(title);
		domainModel.addTodoItem(this, todoList, title);
	}

	private IItemComposite addItemComposite(ITodoItem todoItem) {
		IItemComposite itemComposite = getItemComposite(todoList, todoItem);
		itemListPanel.add(itemComposite.asWidget());
		itemComposites.add(itemComposite);
		itemComposite.addEditListener(this);
		itemComposite.addCheckBoxChangeListener(this);
		return itemComposite;
	}

	public void onEdit(Widget sender) {
		for (IItemComposite itemComposite : itemComposites) {
			if (itemComposite != sender) {
				itemComposite.disableEdit();
			}
		}
	}

	public void onFinishedEdit(Widget sender) {

	}

	private void removeItemComposite(IItemComposite itemComposite) {
		itemListPanel.remove(itemComposite.asWidget());
		todoList.getTodoItems().remove(itemComposite.getTodoItem());
		// TODO call itemComposite.destroy();
	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {

	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		if (sender == addItemTextBox) {
			if (addItemEmpty()) {
				addItemButton.setEnabled(false);
			} else {
				addItemButton.setEnabled(true);
				if (keyCode == KeyboardListener.KEY_ENTER) {
					addItem();
				}
			}
		}
	}

	private boolean addItemEmpty() {
		String text = addItemTextBox.getText();
		return text == null || text.length() == 0;
	}

	public void onFocus(Widget sender) {
		if (sender == addItemTextBox) {
			for (IItemComposite itemComposite : itemComposites) {
				itemComposite.disableEdit();
			}
		}

	}

	public void onLostFocus(Widget sender) {
	}

	public void onCheckBoxChange(Widget sender, boolean checked) {
		if (sender instanceof ItemComposite) {
			if (checked) {
				if (!checkedItemComposites.contains(sender)) {
					checkedItemComposites.add((ItemComposite) sender);
				}
				enableMultiOptions();
			} else {
				checkedItemComposites.remove(sender);
				if (checkedItemComposites.size() == 0) {
					disableMultiOptions();
				}
			}
		}
	}

	private void enableMultiOptions() {
		reloadMoveListBox(todoList, todoLists);
		multiPanel.setVisible(true);
	}

	private void disableMultiOptions() {
		multiPanel.setVisible(false);
	}

	private void deleteMulti() {
		if (checkedItemComposites.size() > 0) {
			Confirm.confirm(textConstants.deleteCheckedConfirmation(), deleteConfirmListener,
					textConstants.deleteCheckedItems(), textConstants.cancel());
		} else {
			GWT.log("For reason we got to deleteMulti, but there are no items checked?", null);
		}
	}

	private ConfirmListener deleteConfirmListener = new ConfirmListener() {
		public void onNo(Widget sender) {
		}

		public void onYes(Widget sender) {
			List<Long> itemIds = new ArrayList<Long>();
			for (IItemComposite itemComposite : checkedItemComposites) {
				itemIds.add(itemComposite.getId());
			}
			domainModel.deleteMultipleTodoItems(this, todoList, itemIds);
			// todoService.deleteMultipleTodoItems(itemIds, deleteMultiCallback);
		}
	};

	private void moveMulti() {
		if (checkedItemComposites.size() > 0) {
			Long destinationTodoListId = Long.valueOf(moveListBox.getValue(moveListBox
					.getSelectedIndex()));
			List<Long> itemIds = new ArrayList<Long>();
			for (IItemComposite itemComposite : checkedItemComposites) {
				itemIds.add(itemComposite.getId());
			}
			domainModel.moveMultipleTodoItems(this, todoList, destinationTodoListId, itemIds);
		} else {
			GWT.log("For reason we got to deleteMulti, " + "but there are no items checked?", null);
		}

	}

	private void startTitleEdit() {
		titleLabel.setVisible(false);
		editTitleComposite.setText(titleLabel.getText());
		editTitleComposite.setVisible(true);
		editTitleComposite.focus();
	}

	private void endTitleEdit() {
		titleLabel.setVisible(true);
		editTitleComposite.setVisible(false);
	}

	private OkCancelListener editTitleOkCancelListener = new OkCancelListener() {
		public void onCancel(Widget sender) {
			endTitleEdit();
		}

		public void onOk(Widget sender) {
			domainModel.updateTodoListTitle(this, todoList, editTitleComposite.getText());
			// todoService.updateTodoListTitle(todoList.getId(), editTitleComposite.getText(),
			// updateTodoListTitleCallback);
		}
	};

	public void onRequestDelete(Widget sender) {
		// Delete to do list
		domainModel.deleteTodoList(this, todoList);
	}

	public Long getTodoListId() {
		return todoList.getId();
	}

	public void addRequestMoveListener(RequestMoveListener listener) {
		requestMoveListeners.add(listener);
	}

	public void onRequestMoveDown(Widget sender) {
		for (RequestMoveListener listener : requestMoveListeners) {
			listener.onRequestMoveDown(this);
		}
	}

	public void onRequestMoveUp(Widget sender) {
		for (RequestMoveListener listener : requestMoveListeners) {
			listener.onRequestMoveUp(this);
		}
	}

	public void onRequestImportText(Object sender) {
		importTextPanel.setVisible(true);
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				importTextArea.setFocus(true);
			}
		});
	}

	public Widget asWidget() {
		return this;
	}

	private void importText() {
		domainModel.importTextList(todoList, importTextArea.getText());
	}

	private void cancelImport() {
		importTextPanel.setVisible(false);
		importTextArea.setText("");
	}

	@Override
	public void onTodoItemDeleted(Object originator, ITodoItem todoItem) {
		// TODO find ItemComposite by todoItem instead
		if (originator instanceof IItemComposite) {
			IItemComposite itemComposite = (IItemComposite) originator;
			removeItemComposite(itemComposite);
			checkedItemComposites.remove(itemComposite);
			if (checkedItemComposites.size() == 0) {
				disableMultiOptions();
			}
		}
	}

	@Override
	public void onTodoItemMoveDown(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {
		if (this.todoList == todoList) {
			refreshAndEdit(todoItem2);
		}
	}

	@Override
	public void onTodoItemMoveUp(Object originator, ITodoList todoList, ITodoItem todoItem1,
			ITodoItem todoItem2) {
		if (this.todoList == todoList) {
			refreshAndEdit(todoItem2);
		}
	}

	@Override
	public void onTodoListItemsFetched(Object originator, ITodoList todoList) {
		if (this.todoList == todoList) {
			// TODO is this ok?
			refresh();
			// processTodoItems();
		}
	}

	@Override
	public void onTodoItemAdded(Object originator, ITodoList todoList, ITodoItem todoItem) {
		addItemComposite(todoItem);
		addItemTextBox.setText("");
		addItemButton.setEnabled(false);
	}

	@Override
	public void onMultipleTodoItemsDelete(Object sender, ITodoList todoList, List<Long> itemIds) {
		if (this.todoList == todoList) {
			for (IItemComposite itemComposite : checkedItemComposites) {
				removeItemComposite(itemComposite);
			}
			checkedItemComposites.clear();
			disableMultiOptions();
		}
	}

	@Override
	public void onMultipleTodoItemsMoved(Object sender, ITodoList todoList,
			Long destinationTodoListId, List<Long> itemIds) {
		if (this.todoList == todoList) {
			for (IItemComposite itemComposite : checkedItemComposites) {
				removeItemComposite(itemComposite);
			}
			checkedItemComposites.clear();
		}
	}

	@Override
	public void onTodoListTitleUpdated(Object sender, ITodoList todoList, String title) {
		if (this.todoList == todoList) {
			titleLabel.setText(title);
			todoList.setTitle(title);
			endTitleEdit();
		}
	}

	@Override
	public void onTodoItemMoved(ITodoList sourceTodoList, ITodoList destinationTodoList,
			ITodoItem todoItemToMove) {
		if (this.todoList == sourceTodoList || this.todoList == destinationTodoList) {
			domainModel.fetchTodoItemsForList(this, todoList);
		}

	}

	@Override
	public void onTodoItemRepositioned(ITodoItem todoItem, ITodoItem beforeTodoItem,
			ITodoList todoList) {
		if (this.todoList == todoList) {
			domainModel.fetchTodoItemsForList(this, todoList);
		}
	}
}
