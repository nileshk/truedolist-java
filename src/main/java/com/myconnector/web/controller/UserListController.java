package com.myconnector.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.myconnector.domain.UserData;
import com.myconnector.service.UserService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class UserListController extends SimpleFormController implements
        Controller {

    static Logger logger = Logger.getLogger(UserListController.class);

    UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserListController() {
        setCommandClass(UserData.class);
    }

    protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors)
            throws Exception {        
        ModelAndView mv = super.showForm(request, response, errors);
        List userList = userService.getList();
        mv.addObject("userList", userList);
        return mv;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        return new UserData();
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        UserData userData = (UserData) command;
        if (userData.getId() == null || userData.getId().equals("")) {
            userService.saveUser(userData);
        } else {
            userService.updateUser(userData);
        }

        return showForm(request, response, errors);
    }

}