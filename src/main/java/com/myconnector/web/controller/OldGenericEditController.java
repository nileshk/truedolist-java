package com.myconnector.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.myconnector.service.OldGenericExtraService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class OldGenericEditController extends SimpleFormController {

    static Logger logger = Logger.getLogger(OldGenericEditController.class);

    protected OldGenericExtraService service;

    public void setService(OldGenericExtraService service) {
        this.service = service;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        String id = request.getParameter("id");
        Object command = null;
        if (id == null || id.equals("")) {
            command = getCommandClass().newInstance();
        } else {
            command = service.getById(id);
        }
        return command;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        String id = null;
        if(BeanUtils.describe(command).containsKey("id")) {
            id = BeanUtils.getProperty(command, "id");
        } else {
            id = request.getParameter("id");
        }
        if (id == null || id.equals("")) {
            service.save(command);
        } else {
            service.update(command);
        }
        return new ModelAndView(new RedirectView(getSuccessView(), false));
    }
}