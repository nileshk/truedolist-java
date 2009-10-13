package com.myconnector.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.myconnector.service.SecurityService;
import com.myconnector.web.PasswordEditCommand;

public class PasswordEditController extends SimpleFormController {

    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public PasswordEditController() {
        super();
        setCommandClass(PasswordEditCommand.class);
    }
       
    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
        PasswordEditCommand passwordEditCommand = (PasswordEditCommand) command;
        securityService.changePasswordForCurrentUser(passwordEditCommand.getNewPassword());
        return super.onSubmit(command);
    }
    
}
