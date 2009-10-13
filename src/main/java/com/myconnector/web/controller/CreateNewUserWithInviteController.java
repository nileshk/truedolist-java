package com.myconnector.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.myconnector.service.SecurityService;
import com.myconnector.web.CreateNewUserCommand;

public class CreateNewUserWithInviteController extends SimpleFormController implements Controller {

    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public CreateNewUserWithInviteController() {
        super();
        setCommandClass(CreateNewUserCommand.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String token = request.getParameter("token");
        CreateNewUserCommand command = new CreateNewUserCommand();
        if (token != null && !token.equals("")) {
            command.setToken(token);
        }
        return command;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            Object commandObject, BindException errors) throws Exception {
        CreateNewUserCommand command = (CreateNewUserCommand) commandObject;
        String token = command.getToken();
        securityService.createNewUserWithInvite(token, command.getUserName(), command.getEmail(),
                command.getPassword());
        return super.onSubmit(request, response, commandObject, errors);
    }

}
