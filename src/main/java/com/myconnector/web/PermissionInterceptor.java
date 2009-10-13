/*
 * Created on Sep 6, 2004
 *
 */
package com.myconnector.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    Logger logger = Logger.getLogger(PermissionInterceptor.class);

    String view;

    private final Map permissionMap = new HashMap();

    /**
     * @param view
     *            The view to set.
     */
    public void setView(String view) {
        this.view = view;
    }

    /**
     * @param permissionMap
     *            The permissionMap to set.
     */
    public void setPermissionMap(Map permissionMap) {
        this.permissionMap.putAll(permissionMap);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        
        String servletPath = request.getServletPath();
        logger.debug("ServletPath: " + servletPath);
        
        String username = (String) WebUtils.getSessionAttribute(request, "username");
        Byte currentUserSecurityLevelByte = (Byte) WebUtils.getSessionAttribute(request, "currentUserSecurityLevel");
        if(currentUserSecurityLevelByte == null) {
            throw new RuntimeException("Security level not set.");
        }
        byte currentUserSecurityLevel = currentUserSecurityLevelByte.byteValue();
                
//        String handlerName = handler.getClass().getName();
//        String classSecurityLevelString = (String) permissionMap.get(handlerName);
//        logger.debug("Permissions for " + handlerName + " = " + classSecurityLevelString);
//        /* if security level not defined, allow access */
//        if(classSecurityLevelString == null) {
//            return true;
//        }

        String pathSecurityLevelString = (String) permissionMap.get(servletPath);
        logger.debug("Permissions for " + servletPath + " = " + pathSecurityLevelString);
        /* if security level not defined, allow access */
        if(pathSecurityLevelString == null) {
            return true;
        }        
        
        // TODO move string to byte conversion to init
        byte pathSecurityLevel = Byte.valueOf(pathSecurityLevelString).byteValue();
        if(currentUserSecurityLevel < pathSecurityLevel) {
            throw new RuntimeException("Security level too low to access this resource.");
        }
        
        return true;
    }
}