package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.domain.UserData;
import com.myconnector.service.SecurityService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class InitializeController implements Controller {

    String view;

    SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setView(String view) {
        this.view = view;
    }

    /*
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        UserData userData = securityService.initialize();
        Map model = new HashMap();
        model.put("userData", userData);
        return new ModelAndView(view, model);
    }

}