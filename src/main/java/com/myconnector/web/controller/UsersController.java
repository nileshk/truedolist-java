package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.service.UserService;

public class UsersController implements Controller {

    private UserService userService;

    private String view;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setView(String view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map map = new HashMap();
        List userList = userService.getList();
        map.put("userList", userList);
        return new ModelAndView(view, map);
    }

}
