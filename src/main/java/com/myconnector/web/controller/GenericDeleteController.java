/*
 * Created on Aug 28, 2004
 *
 */
package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.myconnector.service.GenericService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class GenericDeleteController implements Controller {

    static Logger logger = Logger.getLogger(GenericDeleteController.class);

    GenericService service;
    String view = "home.do";

    /**
     * @param genericService
     *            The genericService to set.
     */
    public void setService(GenericService genericService) {
        this.service = genericService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        String id = req.getParameter("id");
        String returnId = req.getParameter("returnId");
        Map model = new HashMap();
        
        logger.debug("id = " + id);
        logger.debug("submit.delete: " + req.getParameter("submit.delete"));

        // Check for submit.delete which is proof that this is a POST
        if (id != null && req.getParameter("submit.delete") != null) {
            try {
                logger.debug("deleting id = " + id);
                service.deleteById(Long.valueOf(id));
            } catch (HibernateObjectRetrievalFailureException ex) {
                model.put("deleteFailed", "true");
            }
        }
        String returnView = null;
        if (returnId != null && !returnId.equals("")) {
            returnView = view + "?id=" + returnId;
        } else {
            returnView = view;
        }

        return new ModelAndView(new RedirectView(returnView, false));
    }

    /**
     * @param view
     *            The view to set.
     */
    public void setView(String view) {
        this.view = view;
    }
}
