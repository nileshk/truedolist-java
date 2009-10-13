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

import com.myconnector.service.TodoListService;

public class TodoListMoveUpOrDownController implements Controller {

    static Logger logger = Logger.getLogger(TodoListMoveUpOrDownController.class);

    private TodoListService todoListService;
    private String view = "todoLists.do";

    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    public void setView(String view) {
        this.view = view;
    }

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Long.valueOf(req.getParameter("id"));
        String returnId = req.getParameter("returnId");
        Map model = new HashMap();

        logger.debug("id = " + id);
        logger.debug("submit.delete: " + req.getParameter("submit.delete"));

        // Check for submit.moveUp or submit.moveDown which is proof that this
        // is a POST
        if (id != null) {
            if (req.getParameter("submit.moveUp") != null) {
                try {
                    logger.debug("moveUp id = " + id);
                    todoListService.moveUp(id);
                } catch (HibernateObjectRetrievalFailureException ex) {
                    model.put("moveUpFailed", "true");
                }
            } else if (req.getParameter("submit.moveDown") != null) {
                try {
                    logger.debug("moveUp id = " + id);
                    todoListService.moveDown(id);
                } catch (HibernateObjectRetrievalFailureException ex) {
                    model.put("moveDownFailed", "true");
                }                
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

}
