package com.myconnector.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myconnector.client.domain.TodoItemClient;
import com.myconnector.client.domain.TwoTodoClientItems;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.service.TodoServiceGwtAsync;

public class DomainModelImpl extends BaseDomainModel implements DomainModel {

	private TodoServiceGwtAsync todoService;
	private List<TodoListsInitListener> todoListsInitListeners = new ArrayList<TodoListsInitListener>();
	private List<TodoListsChangeListener> todoListsChangeListeners = new ArrayList<TodoListsChangeListener>();
	private List<ITodoList> todoListProxies = new ArrayList<ITodoList>();
	private ITodoList selectedTodoList = null;

	public void setTodoService(TodoServiceGwtAsync todoService) {
		this.todoService = todoService;
	}

	public void init() {
		begin();
		todoService.getTodoListsOnly(getTodoListsOnlyCallback);
	}

	public void reload() {
		if (selectedTodoList != null) {
			final Long selectedTodoListId = selectedTodoList.getId();
			begin();
			todoService.getTodoListsOneSelected(selectedTodoListId,
					new AsyncCallback<List<ITodoList>>() {
						public void onFailure(Throwable caught) {
							handle(caught);
						}

						public void onSuccess(List<ITodoList> result) {
							try {
								todoListProxies.clear();
								for (ITodoList todoList : result) {
									TodoListProxy todoListProxy = new TodoListProxy(todoList);
									// TODO events for todoListProxy
									todoListProxies.add(todoListProxy);
								}

								// XXX Do we need to use DeferredCommand here?
								for (TodoListsInitListener listener : todoListsInitListeners) {
									listener.onTodoListsInit(todoListProxies);
								}

								for (ITodoList todoListProxy : todoListProxies) {
									if (todoListProxy.getId().equals(selectedTodoListId)) {
										selectedTodoList(todoListProxy, this);
										break;
									}
								}
							} finally {
								end();
							}
						}
					});
		} else {
			init();
		}
	}

	private AsyncCallback<List<ITodoList>> getTodoListsOnlyCallback = new AsyncCallback<List<ITodoList>>() {
		public void onFailure(Throwable caught) {
			handle(caught);
		}

		public void onSuccess(List<ITodoList> result) {
			try {
				todoListProxies.clear();
				for (ITodoList todoList : result) {
					TodoListProxy todoListProxy = new TodoListProxy(todoList);
					// TODO events for todoListProxy
					todoListProxies.add(todoListProxy);
				}

				// XXX Do we need to use DeferredCommand here?
				for (TodoListsInitListener listener : todoListsInitListeners) {
					listener.onTodoListsInit(todoListProxies);
				}
			} finally {
				end();
			}
		}
	};

	public void addTodoListsInitListener(TodoListsInitListener listener) {
		todoListsInitListeners.add(listener);
	}

	public void addTodoListsChangeListener(TodoListsChangeListener listener) {
		todoListsChangeListeners.add(listener);
	}

	public void removeTodoListsChangeListener(TodoListsChangeListener listener) {
		todoListsChangeListeners.remove(listener);
	}

	public void selectedTodoList(final ITodoList todoList, final Object sender) {
		selectedTodoList = todoList;
		executeOnListeners(new ListenerCommand() {
			public void execute(TodoListsChangeListener listener) {
				listener.onTodoListSelected(sender, todoList);
			}
		});
	}

	public void deleteTodoList(final Object sender, final ITodoList todoList) {
		begin();
		todoService.deleteTodoList(todoList.getId(), new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					todoListProxies.remove(todoList);
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoListDeleted(sender, todoList);
						}
					});
				} finally {
					end();
				}
			}
		});
	}

	public void deleteTodoItem(final Object sender, final ITodoItem todoItem) {
		begin();
		todoService.deleteTodoItem(todoItem.getId(), new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					for (ITodoList todoList : todoListProxies) {
						if (todoList.isTodoItemsLoaded()) {
							List<ITodoItem> todoItems = todoList.getTodoItems();
							if (todoItems != null) {
								if (todoItems.remove(todoItem)) {
									break;
								}
							}
						}
					}
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoItemDeleted(sender, todoItem);
						}
					});
				} finally {
					end();
				}
			}
		});
	}

	public void addTodoList(final Object sender, String title) {
		begin();
		todoService.addTodoList(title, new AsyncCallback<ITodoList>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(final ITodoList result) {
				try {
					todoListProxies.add(new TodoListProxy(result));
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoListAdded(sender, result);
						}
					});
				} finally {
					end();
				}
			}
		});
	}

	public void updateTodoItemTitle(final Object sender, final ITodoItem todoItem,
			final String title) {
		begin();
		todoService.updateTodoItem(todoItem.getId(), title, new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					todoItem.setTitle(title);
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoItemModified(sender, todoItem);
						}
					});
				} finally {
					end();
				}
			}
		});
	}

	public void moveTodoItemUp(final Object sender, final ITodoList todoList, ITodoItem todoItem) {
		begin();
		todoService.moveTodoItemUp(todoItem.getId(), new AsyncCallback<TwoTodoClientItems>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(TwoTodoClientItems result) {
				try {
					if (result != null) {
						// TODO do we really need the result?
						ITodoItem todoItemToFind1 = result.getTodoItem1();
						// ITodoItem todoItemToFind2 = result.getTodoItem2();

						final List<ITodoItem> todoItems = todoList.getTodoItems();
						for (int i = 0; i < todoItems.size(); i++) {
							ITodoItem todoItem = todoItems.get(i);
							if (todoItem.getId().equals(todoItemToFind1.getId())) {
								final ITodoItem todoItem1 = todoItems.remove(i);
								final ITodoItem todoItem2 = todoItems.remove(i);
								todoItems.add(i, todoItem1);
								todoItems.add(i, todoItem2);
								executeOnListeners(new ListenerCommand() {
									public void execute(TodoListsChangeListener listener) {
										listener.onTodoItemMoveUp(sender, todoList, todoItem1,
												todoItem2);
									}
								});
								break;
							}
						}

					}
				} finally {
					end();
				}
			}
		});

	}

	public void moveTodoItemDown(final Object sender, final ITodoList todoList, ITodoItem todoItem) {
		begin();
		todoService.moveTodoItemDown(todoItem.getId(), new AsyncCallback<TwoTodoClientItems>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(TwoTodoClientItems result) {
				try {
					if (result != null) {
						// TODO do we really need the result?
						// ITodoItem todoItemToFind1 = result.getTodoItem1();
						ITodoItem todoItemToFind2 = result.getTodoItem2();

						List<ITodoItem> todoItems = todoList.getTodoItems();
						for (int i = 0; i < todoItems.size(); i++) {
							ITodoItem todoItem = todoItems.get(i);
							if (todoItem.getId().equals(todoItemToFind2.getId())) {
								final ITodoItem todoItem2 = todoItems.remove(i);
								final ITodoItem todoItem1 = todoItems.remove(i);
								todoItems.add(i, todoItem2);
								todoItems.add(i, todoItem1);
								executeOnListeners(new ListenerCommand() {
									public void execute(TodoListsChangeListener listener) {
										listener.onTodoItemMoveDown(sender, todoList, todoItem1,
												todoItem2);
									}
								});
								break;
							}
						}
					}
				} finally {
					end();
				}
			}
		});

	}

	public void fetchTodoItemsForList(final Object sender, final ITodoList todoList) {
		begin();
		todoService.getTodoItemsForList(todoList.getId(), new AsyncCallback<List<ITodoItem>>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(List<ITodoItem> result) {
				try {
					List<ITodoItem> todoItems = new ArrayList<ITodoItem>();
					for (ITodoItem todoItem : result) {
						todoItems.add(new TodoItemProxy(todoItem));
					}
					todoList.setTodoItems(todoItems);
					todoList.setTodoItemsLoaded(true);
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoListItemsFetched(sender, todoList);
						}
					});
				} finally {
					end();
				}
			}
		});
	}

	public void addTodoItem(final Object sender, final ITodoList todoList, final String title) {
		begin();
		todoService.addTodoItem(todoList.getId(), title, new AsyncCallback<Long>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			// TODO return TodoItemClient object back from service because we need
			// Position
			public void onSuccess(Long todoItemId) {
				try {
					final ITodoItem todoItem = new TodoItemProxy(new TodoItemClient());
					todoItem.setId(todoItemId);
					todoItem.setTitle(title);
					todoList.getTodoItems().add(todoItem);
					for (final TodoListsChangeListener listener : todoListsChangeListeners) {
						DeferredCommand.addCommand(new Command() {
							public void execute() {
								listener.onTodoItemAdded(sender, todoList, todoItem);
							}
						});
					}
				} finally {
					end();
				}
			}
		});
	}

	public void deleteMultipleTodoItems(final Object sender, final ITodoList todoList,
			final List<Long> itemIds) {
		begin();
		todoService.deleteMultipleTodoItems(itemIds, new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object results) {
				try {
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							todoList.setTodoItemsLoaded(false);
							listener.onMultipleTodoItemsDelete(sender, todoList, itemIds);
						}
					});
				} finally {
					end();
				}
			}
		});

	}

	public void moveMultipleTodoItems(final Object sender, final ITodoList todoList,
			final Long destinationTodoListId, final List<Long> itemIds) {
		begin();
		todoService.moveMultipleTodoItems(itemIds, destinationTodoListId,
				new AsyncCallback<Object>() {

					public void onFailure(Throwable caught) {
						handle(caught);
					}

					public void onSuccess(Object results) {
						try {
							todoList.setTodoItemsLoaded(false);
							// Pass in action destionTodoList object so we don't have to loop here
							for (ITodoList list : todoListProxies) {
								if (list.getId().equals(destinationTodoListId)) {
									// Invalid list items moved to so that it gets reloaded
									list.setTodoItemsLoaded(false);
								}
							}
							executeOnListeners(new ListenerCommand() {
								public void execute(TodoListsChangeListener listener) {
									listener.onMultipleTodoItemsMoved(sender, todoList,
											destinationTodoListId, itemIds);
								}
							});
						} finally {
							end();
						}
					}

				});
	}

	public void updateTodoListTitle(final Object sender, final ITodoList todoList,
			final String title) {
		begin();
		todoService.updateTodoListTitle(todoList.getId(), title, new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object results) {
				try {
					todoList.setTitle(title);
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							listener.onTodoListTitleUpdated(sender, todoList, title);
						}
					});
				} finally {
					end();
				}
			}
		});

	}

	/**
	 * Executes a command on all change listeners
	 * 
	 * @param command
	 */
	private void executeOnListeners(final ListenerCommand command) {
		for (final TodoListsChangeListener listener : todoListsChangeListeners) {
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					command.execute(listener);
				}
			});
		}
	}

	/**
	 * Command to execute on a change listener
	 */
	private interface ListenerCommand {
		void execute(TodoListsChangeListener listener);
	}

	public void importTextList(final ITodoList todoList, String text) {
		begin();
		todoService.addTodoItemsFromTextArea(todoList.getId(), text,
				new AsyncCallback<List<ITodoItem>>() {
					public void onFailure(Throwable caught) {
						handle(caught);
					}

					public void onSuccess(List<ITodoItem> result) {
						try {
							List<ITodoItem> todoItems = new ArrayList<ITodoItem>();
							for (ITodoItem todoItem : result) {
								todoItems.add(new TodoItemProxy(todoItem));
							}
							todoList.setTodoItems(todoItems);
							todoList.setTodoItemsLoaded(true);
							executeOnListeners(new ListenerCommand() {
								public void execute(TodoListsChangeListener listener) {
									listener.onTodoListItemsFetched(this, todoList);
								}
							});
						} finally {
							end();
						}

					}
				});
	}

	public void moveTodoItem(final ITodoList sourceTodoList, final ITodoList destinationTodoList,
			final ITodoItem todoItemToMove) {
		begin();
		todoService.moveTodoItem(todoItemToMove.getId(), destinationTodoList.getId(),
				new AsyncCallback<Object>() {

					public void onFailure(Throwable caught) {
						handle(caught);
					}

					public void onSuccess(Object results) {
						try {
							sourceTodoList.setTodoItemsLoaded(false);
							destinationTodoList.setTodoItemsLoaded(false);
							executeOnListeners(new ListenerCommand() {
								public void execute(TodoListsChangeListener listener) {
									listener.onTodoItemMoved(sourceTodoList, destinationTodoList,
											todoItemToMove);
								}
							});
						} finally {
							end();
						}
					}

				});
	}

	public void repositionTodoItemBefore(final ITodoItem todoItem, final ITodoItem beforeTodoItem,
			final ITodoList todoList) {
		todoService.repositionTodoItemBefore(todoItem.getId(), beforeTodoItem.getId(),
				new AsyncCallback<Object>() {
					public void onFailure(Throwable caught) {
						handle(caught);
					}

					public void onSuccess(Object result) {
						todoList.setTodoItemsLoaded(false);
						executeOnListeners(new ListenerCommand() {
							public void execute(TodoListsChangeListener listener) {
								listener.onTodoItemRepositioned(todoItem, beforeTodoItem, todoList);
							}
						});
					}
				});

	}

	public void repositionTodoListBefore(final ITodoList todoList, final ITodoList beforeTodoList) {
		begin();
		todoService.repositionTodoListBefore(todoList.getId(), beforeTodoList.getId(),
				new AsyncCallback<Object>() {
					public void onFailure(Throwable caught) {
						handle(caught);
					}

					public void onSuccess(Object result) {
						try {
							reload();
							// executeOnListeners(new ListenerCommand() {
							// public void execute(TodoListsChangeListener listener) {
							// listener.onTodoListRepositioned(todoList, beforeTodoList);
							// }
							// });
						} finally {
							end();
						}
					}
				});

	}

	public void setHighlightTodoItem(final Object sender, final ITodoItem todoItem,
			final boolean highlight) {
		begin();
		todoService.highlightTodoItem(todoItem.getId(), highlight, new AsyncCallback<Object>() {
			public void onFailure(Throwable caught) {
				handle(caught);
			}

			public void onSuccess(Object result) {
				try {
					executeOnListeners(new ListenerCommand() {
						public void execute(TodoListsChangeListener listener) {
							todoItem.setHighlighted(highlight);
							listener.onTodoItemModified(sender, todoItem);
						}
					});
				} finally {
					end();
				}

			}
		});
	}

}
