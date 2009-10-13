/*
 * Created on Aug 28, 2004
 *
 */
package com.myconnector.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.myconnector.domain.Login;
import com.myconnector.domain.UserCookie;
import com.myconnector.domain.UserData;
import com.myconnector.service.SecurityService;
import com.myconnector.service.SecurityServiceImpl;
import com.myconnector.util.HttpSessionThreadLocal;
import com.myconnector.web.UserCookieCheck;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class LoginController extends SimpleFormController {

    // TODO move these constants into a file and package specific for comments
    public static final String USER_ID = "sessionUserId";
    public static final String USER_NAME = "username";
    public static final String USER_SECURITY_LEVEL = "currentUserSecurityLevel";
    public static final String USER_COOKIE = "sessionUserCookie";
    public static final String USER_COOKIE_NAME = "userCookie";

    static Logger logger = Logger.getLogger(LoginController.class);

    private SecurityService securityService;
    private UserCookieCheck userCookieCheck;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setUserCookieCheck(UserCookieCheck userCookieCheck) {
        this.userCookieCheck = userCookieCheck;
    }

    /*
     * @see org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.validation.BindException)
     */
    protected ModelAndView showForm(HttpServletRequest req, HttpServletResponse res,
            BindException errors) throws Exception {
        logger.debug("ShowForm RequestURI: " + req.getRequestURI());
        logger.debug("ShowForm RequestURL: " + req.getRequestURL());

        userCookieCheck.checkForUserCookie(req, res);
        setCounts(req);
        return (super.showForm(req, res, errors));
    }

    private void setCounts(HttpServletRequest req) {
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            Object command, BindException errors) throws Exception {
        HttpSessionThreadLocal.set(request.getSession());

        logger.debug("Submit RequestURI: " + request.getRequestURI());
        logger.debug("Submit RequestURL: " + request.getRequestURL());

        setCounts(request);
        Login login = (Login) command;
        UserData userData = securityService.login(login.getUsername(), login.getPassword());
        if (userData != null) {
            // getServletContext().setAttribute("username",
            // login.getUsername());
            saveUserInHttpSession(request, userData);
            String requestedAction = (String) WebUtils.getSessionAttribute(request,
                    "requestedAction");
            Map requestedActionParameterMap = (Map) WebUtils.getSessionAttribute(request,
                    "requestedActionParameterMap");
            if (requestedActionParameterMap != null) {
                logger.debug("Map size: " + String.valueOf(requestedActionParameterMap.size()));
            }

            // Cookie
            if (login.getRemember() != null && login.getRemember().booleanValue()) {
                UserCookie cookie = securityService.getCookie(userData.getId());
                String cookieValue = cookie.getId() + SecurityServiceImpl.COOKIE_SPLITTER + userData.getId();
                CookieGenerator cookieGenerator = new CookieGenerator();
                cookieGenerator.setCookieMaxAge(1209600); // 2 weeks
                cookieGenerator.setCookiePath(CookieGenerator.DEFAULT_COOKIE_PATH);
                cookieGenerator.setCookieName(USER_COOKIE_NAME);
                cookieGenerator.setCookieSecure(false);
                cookieGenerator.addCookie(response, cookieValue);
                // WebUtils.setSessionAttribute(request, USER_COOKIE,
                // cookieValue);
                logger.debug("cookie id = " + cookie.getId());
                logger.debug("cookie value = " + cookieValue);
            }

            if (requestedAction == null) {
                return new ModelAndView(getSuccessView(), errors.getModel());
            } else {
                WebUtils.setSessionAttribute(request, "requestedAction", null);
                WebUtils.setSessionAttribute(request, "requestedActionParameterMap", null);
                return new ModelAndView(new RedirectView(requestedAction, true),
                        requestedActionParameterMap);
            }
        } else {
        	errors.reject("errors.invalid.userpassword");
        	//return new ModelAndView()
		}
        return new ModelAndView(getFormView(), errors.getModel());

    }

    public static void saveUserInHttpSession(HttpServletRequest request, UserData userData) {
        WebUtils.setSessionAttribute(request, USER_ID, userData.getId());
        WebUtils.setSessionAttribute(request, USER_NAME, userData.getUserLogin());
        WebUtils.setSessionAttribute(request, USER_SECURITY_LEVEL, new Byte(userData
                .getSecurityLevel()));
    }
}