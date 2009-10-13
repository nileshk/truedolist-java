/*
 * Created on Aug 28, 2004
 *
 */
package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.service.OldGenericService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class OldGenericListController implements Controller {

    static Logger logger = Logger.getLogger(OldGenericListController.class);

    OldGenericService service;

    protected String view;

    protected String listName = "list";

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setView(String view) {
        this.view = view;
    }

    /**
     * @param genericService
     *            The genericService to set.
     */
    public void setService(OldGenericService genericService) {
        this.service = genericService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List list = service.getList();
        Map model = new HashMap();
        model.put(listName, list);
        return new ModelAndView(view, model);
    }
}