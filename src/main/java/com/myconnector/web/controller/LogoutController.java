/*
 * Created on Aug 28, 2004
 *
 */
package com.myconnector.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.myconnector.service.SecurityService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com
 */
public class LogoutController extends WebApplicationObjectSupport implements Controller {

    static Logger logger = Logger.getLogger(LogoutController.class);

    SecurityService securityService;

    String view;

    /**
     * @param securityService
     *            The securityService to set.
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param view
     *            The view to set.
     */
    public void setView(String view) {
        this.view = view;
    }

    /*
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // HttpSessionThreadLocal.set(request.getSession());
        String username = (String) WebUtils.getSessionAttribute(request, LoginController.USER_NAME);
        logger.info("Logging out user:" + username);
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginController.USER_COOKIE_NAME)) {
                    cookieValue = cookie.getValue();
                    clearCookie(response);
                    break;
                }
            }
        }
        securityService.logout(cookieValue);
        WebUtils.setSessionAttribute(request, LoginController.USER_NAME, null);
        WebUtils.setSessionAttribute(request, LoginController.USER_ID, null);
        WebUtils.setSessionAttribute(request, LoginController.USER_SECURITY_LEVEL, null);
        request.getSession().invalidate();
        // getServletContext().removeAttribute("username");
        return new ModelAndView(view);
    }

    public static void clearCookie(HttpServletResponse response) {
        logger.debug("Clear cookie on browser");
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieMaxAge(0);
        cookieGenerator.setCookiePath(CookieGenerator.DEFAULT_COOKIE_PATH);
        cookieGenerator.setCookieName(LoginController.USER_COOKIE_NAME);
        cookieGenerator.setCookieSecure(false);
        cookieGenerator.addCookie(response, "");
    }
}