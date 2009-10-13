package com.myconnector.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.myconnector.service.InviteService;
import com.myconnector.service.SecurityService;

public class CreateNewUserValidator implements Validator {

    private InviteService inviteService;
    private SecurityService securityService;

    public void setInviteService(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public boolean supports(Class clazz) {
        return CreateNewUserCommand.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "token", "error.createNewUser.missingToken");
        // TODO set different error messages?
        ValidationUtils
                .rejectIfEmpty(errors, "password", "error.passwordEdit.currentPasswordEmpty");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.passwordEdit.newPasswordEmpty");
        ValidationUtils.rejectIfEmpty(errors, "passwordConfirmation",
                "error.passwordEdit.newPasswordConfirmationEmpty");

        CreateNewUserCommand command = (CreateNewUserCommand) obj;
        if (command != null && command.getPassword() != null
                && !command.getPassword().equals(command.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation",
                    "error.passwordEdit.newPasswordConfirmationDoesNotMatch");
        }

        if (inviteService.getInviteByToken(command.getToken()) == null) {
            errors.rejectValue("token", "error.createNewUser.invalidToken");
        }
        if (command.getUserName() == null || command.getUserName().equals("")) {
            errors.rejectValue("userName", "error.createNewUser.userNameBlank");
        } else if (securityService.isExistingUser(command.getUserName())) {
            errors.rejectValue("userName", "error.createNewUser.existingUserName");
        }
        if (command.getEmail() == null || command.getEmail().equals("")) {
            errors.rejectValue("email", "error.createNewUser.emailBlank");
        }
                
    }

}
