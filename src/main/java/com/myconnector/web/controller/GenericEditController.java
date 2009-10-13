package com.myconnector.web.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.myconnector.domain.interfaces.HasId;
import com.myconnector.service.GenericService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class GenericEditController extends SimpleFormController implements Controller {

    static Logger logger = Logger.getLogger(GenericEditController.class);

    protected GenericService service;

    private String parentIdProperty = null;

    public void setService(GenericService service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        Object command = null;
        if (id == null || id.equals("")) {
            command = getCommandClass().newInstance();
        } else {
            command = service.getById(Long.valueOf(id));
        }
        if (parentIdProperty != null) {
            handleParentId(request, command);
        }
        return command;
    }

    private void handleParentId(HttpServletRequest request, Object command) {
        if (command != null) {
            Long parentId = Long.valueOf(request.getParameter("parentId"));
            if (parentId != null) {
                BeanWrapper beanWrapper = new BeanWrapperImpl(command);
                beanWrapper.setPropertyValue(parentIdProperty, parentId);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            Object command, BindException errors) throws Exception {
        Serializable commandSerializable = (Serializable) command;
        Long id = null;
        if (commandSerializable instanceof HasId) {
            id = ((HasId) commandSerializable).getId();
        } else {
            id = Long.valueOf(request.getParameter("id"));
        }
        if (id == null) {
            service.save(commandSerializable);
        } else {
            service.update(commandSerializable);
        }
        return new ModelAndView(new RedirectView(getSuccessView(), false));
    }
}