package com.myconnector.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.myconnector.domain.UserData;
import com.myconnector.service.SecurityService;
import com.myconnector.util.HttpSessionThreadLocal;
import com.myconnector.web.controller.LoginController;
import com.myconnector.web.controller.LogoutController;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class LoginInterceptor extends HandlerInterceptorAdapter implements UserCookieCheck {

    static Logger logger = Logger.getLogger(LoginInterceptor.class);

    private String view;

    private SecurityService securityService;

    public void setView(String view) {
        this.view = view;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        HttpSessionThreadLocal.set(request.getSession());
        String username = (String) WebUtils.getSessionAttribute(request, LoginController.USER_NAME);
        Long userId = (Long) WebUtils.getSessionAttribute(request, LoginController.USER_ID);
        logger.debug("Intercepting username: " + username);
        logger.debug("Intercepting for object: " + handler.getClass().getName());
        if (username == null && userId == null) {
            if (checkForUserCookie(request, response)) {
                return true;
            }

            storeRequestInSession(request);
            logger.debug("username is null.  Not logged in.");
            ModelAndView modelAndView = new ModelAndView(view);
            throw new ModelAndViewDefiningException(modelAndView);
        } else {
            logger.debug("Logged in, returning true");
            return true;
        }
    }

    private void storeRequestInSession(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String requestedAction = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        WebUtils.setSessionAttribute(request, "requestedAction", requestedAction);
        logger.debug("requestedAction: " + requestedAction);
        WebUtils.setSessionAttribute(request, "requestedActionParameterMap", WebUtils
                .getParametersStartingWith(request, null));
        logger.debug("Map size: " + String.valueOf(request.getParameterMap().size()));
    }

    public boolean checkForUserCookie(HttpServletRequest request, HttpServletResponse response) {
        // TODO just grab cookieValue from ThreadLocal because HttpSessionServletFilter already got it for us
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginController.USER_COOKIE_NAME)) {
                    cookieValue = cookie.getValue();
                    if (cookieValue != null && !cookieValue.equals("")) {
                        logger.debug("Attempting login with cookie value = " + cookieValue);
                        UserData userData = securityService.loginWithCookie(cookieValue);
                        if (userData != null) {
                            LoginController.saveUserInHttpSession(request, userData);
                            logger.debug("Logged in using cookie, returning true");
                            return true;
                        } else {
                            LogoutController.clearCookie(response);
                        }
                    }
                    break;
                }

            }
        }
        return false;
    }
}
