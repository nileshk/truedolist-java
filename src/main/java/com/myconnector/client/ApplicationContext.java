package com.myconnector.client;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.myconnector.client.composite.IItemComposite;
import com.myconnector.client.composite.IItemListComposite;
import com.myconnector.client.composite.IListPanelComposite;
import com.myconnector.client.composite.ITodoListComposite;
import com.myconnector.client.composite.ItemComposite;
import com.myconnector.client.composite.ItemListComposite;
import com.myconnector.client.composite.ListPanelComposite;
import com.myconnector.client.composite.TodoImageBundle;
import com.myconnector.client.composite.TodoListComposite;
import com.myconnector.client.composite.settings.ChangeEmailComposite;
import com.myconnector.client.composite.settings.ChangePasswordComposite;
import com.myconnector.client.composite.settings.ISettingsComposite;
import com.myconnector.client.composite.settings.SettingsComposite;
import com.myconnector.client.domain.interfaces.ITodoItem;
import com.myconnector.client.domain.interfaces.ITodoList;
import com.myconnector.client.model.DomainModelImpl;
import com.myconnector.client.model.UserDomainModelImpl;
import com.myconnector.client.service.ServiceFactory;
import com.myconnector.client.service.TodoServiceGwtAsync;
import com.myconnector.client.service.UserServiceGwtAsync;

public class ApplicationContext {

	private ServiceFactory serviceFactory;
	private UserServiceGwtAsync userService;
	private TodoServiceGwtAsync todoService;
	private DomainModelImpl domainModel;
	private UserDomainModelImpl userDomainModel;
	private PickupDragController dragController;
	private TodoImageBundle todoImageBundle;
	private TextConstants textConstants;
	private ISettingsComposite settingsComposite;

	public ApplicationContext() {
		dragController = new PickupDragController(RootPanel.get(), false);
		todoImageBundle = GWT.create(TodoImageBundle.class);
		textConstants = GWT.create(TextConstants.class);

		serviceFactory = new ServiceFactory();
		userService = serviceFactory.getUserService();
		todoService = serviceFactory.getTodoService();
		domainModel = new DomainModelImpl();
		domainModel.setTodoService(todoService);
		userDomainModel = new UserDomainModelImpl();
		userDomainModel.setUserService(userService);

		ApplicationMain applicationMain = new ApplicationMain() {
			@Override
			protected IListPanelComposite getListPanelComposite(List<ITodoList> todoLists) {
				return ApplicationContext.this.getListPanelComposite(todoLists);
			}

			@Override
			protected IItemListComposite getItemListComposite(ITodoList todoList,
					List<ITodoList> todoListsIn) {
				return ApplicationContext.this.getItemListComposite(todoList, todoListsIn);
			}

			@Override
			protected ISettingsComposite getSettingsComposite() {
				return ApplicationContext.this.getSettingsComposite();
			}
		};
		applicationMain.setDomainModel(domainModel);
		applicationMain.setUserService(userService);
		applicationMain.setTodoService(todoService);
		applicationMain.setUserDomainModel(userDomainModel);
		applicationMain.setTextConstants(textConstants);
		applicationMain.init();
	}

	private ISettingsComposite getSettingsComposite() {
		if (settingsComposite == null) {
			SettingsComposite concreteSettingsComposite = new SettingsComposite();
			concreteSettingsComposite.setTextConstants(textConstants);
			
			ChangePasswordComposite changePasswordComposite = new ChangePasswordComposite();
			changePasswordComposite.setTextConstants(textConstants);
			changePasswordComposite.setUserDomainModel(userDomainModel);			
			changePasswordComposite.init();
			concreteSettingsComposite.setChangePasswordComposite(changePasswordComposite);
			
			ChangeEmailComposite changeEmailComposite = new ChangeEmailComposite();
			changeEmailComposite.setTextConstants(textConstants);
			changeEmailComposite.setUserDomainModel(userDomainModel);
			changeEmailComposite.init();
			concreteSettingsComposite.setChangeEmailComposite(changeEmailComposite);
			
			concreteSettingsComposite.init();
			settingsComposite = concreteSettingsComposite;			
		}
		return settingsComposite;
	}

	private IListPanelComposite getListPanelComposite(List<ITodoList> todoLists) {
		ListPanelComposite listPanelComposite = new ListPanelComposite(todoLists) {
			@Override
			protected ITodoListComposite createTodoListComposite(ITodoList todoList) {
				return ApplicationContext.this.getTodoListComposite(todoList);
			}
		};
		listPanelComposite.setDomainModel(domainModel);
		listPanelComposite.setTextConstants(textConstants);
		listPanelComposite.init();
		return listPanelComposite;
	}

	private ITodoListComposite getTodoListComposite(ITodoList todoList) {
		TodoListComposite todoListComposite = new TodoListComposite(todoList, todoImageBundle);
		todoListComposite.setDomainModel(domainModel);
		todoListComposite.setTextConstants(textConstants);
		todoListComposite.setDragController(dragController);
		dragController.makeDraggable(todoListComposite);
		return todoListComposite;
	}

	private IItemListComposite getItemListComposite(ITodoList todoList, List<ITodoList> todoListsIn) {
		ItemListComposite itemListComposite = new ItemListComposite(todoList, todoListsIn) {
			@Override
			protected IItemComposite getItemComposite(ITodoList todoList, ITodoItem todoItem) {
				return ApplicationContext.this.getItemComposite(todoList, todoItem);
			}
		};
		itemListComposite.setDomainModel(domainModel);
		itemListComposite.setTextConstants(textConstants);
		itemListComposite.init();
		return itemListComposite;
	}

	private IItemComposite getItemComposite(ITodoList todoList, ITodoItem todoItem) {
		ItemComposite itemComposite = new ItemComposite(todoList, todoItem, todoImageBundle);
		itemComposite.setDomainModel(domainModel);
		itemComposite.setTextConstants(textConstants);
		itemComposite.init();
		itemComposite.setDragController(dragController);
		dragController.makeDraggable(itemComposite);
		return itemComposite;
	}
}
