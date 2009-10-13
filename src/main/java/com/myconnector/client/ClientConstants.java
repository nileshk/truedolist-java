package com.myconnector.client;

public interface ClientConstants {

    static final String USER_COOKIE_NAME = "userCookie";    
    static final String SERVLET_NAME = "";
    static final String LOGIN_PAGE_URL = SERVLET_NAME + "/home.do";
    static final String LOGOUT_PAGE_URL = SERVLET_NAME + "/logout.do";
    static final int SESSION_CHECK_INTERVAL = 25 * 60 * 1000; // Timeout in milliseconds
    static final String USER_NOT_LOGGED_IN_CODE = "user.notLoggedIn";

    // HTML constants
    static final String HTML_NON_BREAKING_SPACE = "&nbsp;";    
    
    // CSS constants
    static final String CSS_DRAGDROP_POSITIONER = "dragdrop-positioner";
    static final String CSS_TODOLIST_PREVIEW_DROP_ITEM = "todoListPreviewDropItem";
    static final String CSS_TODO_LIST_LABEL = "todoListLabel";
    static final String CSS_TODO_ITEM_LABEL = "todoItemLabel";
    static final String CSS_TODO_LIST_HEADER_LABEL = "todoListHeaderLabel";
    static final String CSS_CLICKABLE_LABEL = "clickableLabel";
    static final String CSS_SETTINGS_HEADER_LABEL = "settingsHeaderLabel";
    static final String CSS_VALIDATION_ERROR_LABEL = "validationErrorLabel";
	static final String CSS_SETTING_CHANGE_SUCCESS_MESSAGE = "settingChangeSuccessMessage";
	static final String CSS_TODO_ITEM_HIGHLIGHTED = "todoItemHighlighted";
    	
}
