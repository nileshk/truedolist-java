/*
 * Created on Sep 5, 2004
 *
 */
package com.myconnector.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.myconnector.web.controller.LoginController;

/**
 * 
 * @author Nil
 */
public class HttpSessionThreadLocal {

	// TODO Use WeakReference to hold threadlocal?
	
    static Logger logger = Logger.getLogger(HttpSessionThreadLocal.class);
    
    protected static ThreadLocal<HttpSession> httpSessionThreadLocal = new ThreadLocal<HttpSession>();

    static public void set(HttpSession httpSession) {
        httpSessionThreadLocal.set(httpSession);
    }

    static private HttpSession getHttpSession() {
        return ((HttpSession) httpSessionThreadLocal.get());
    }

    static public Object getAttribute(String name) {
        HttpSession httpSession = getHttpSession();
        if(httpSession != null) {
            return(getHttpSession().getAttribute(name));
        } else {
            return null;
        }
    }
    
    static public void removeAttribute(String name) {
        getHttpSession().removeAttribute(name);
    }
    
    static public void setAttribute(String name, Object obj) {
        getHttpSession().setAttribute(name, obj);
    }
    
    static public void clear() {
        httpSessionThreadLocal.set(null);
    }
    
    static public Long getUserId() {
        return (Long) getAttribute(LoginController.USER_ID);
    }
    
    static public String getUsername() {
        return (String) getAttribute(LoginController.USER_NAME);
    }
    
    static public String getUserCookieValue() {
        return (String) getAttribute(LoginController.USER_COOKIE);
    }
}