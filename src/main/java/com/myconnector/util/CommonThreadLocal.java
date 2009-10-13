/*
 * Created on Aug 9, 2004
 *
 */
package com.myconnector.util;

import org.apache.log4j.Logger;

/**
 * Common class for holding ThreadLocal variables
 * 
 * @author Nilesh
 */
public class CommonThreadLocal {

    static Logger logger = Logger.getLogger(CommonThreadLocal.class);

    protected static ThreadLocal<String> cookieValueThreadLocal = new ThreadLocal<String>();

    static public String getCookieValue() {
        return cookieValueThreadLocal.get();
    }

    static public void setCookieValue(String cookieValue) {
        cookieValueThreadLocal.set(cookieValue);
    }

    static public void clear() {
        cookieValueThreadLocal.set(null);
    }

    // TODO delete getUsername and getUserId because the are in HttpSessionThreadLocal

    public static String getUsername() {
        return HttpSessionThreadLocal.getUsername();
    }

    public static Long getUserId() {
        return HttpSessionThreadLocal.getUserId();
    }

}