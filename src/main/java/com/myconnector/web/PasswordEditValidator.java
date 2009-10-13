package com.myconnector.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.myconnector.service.SecurityService;

public class PasswordEditValidator implements Validator {

    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return PasswordEditCommand.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "currentPassword",
                "error.passwordEdit.currentPasswordEmpty");
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.passwordEdit.newPasswordEmpty");
        ValidationUtils.rejectIfEmpty(errors, "newPasswordConfirmation",
                "error.passwordEdit.newPasswordConfirmationEmpty");
        PasswordEditCommand command = (PasswordEditCommand) obj;
        // Confirm that user confirmed the new password correctly
        if (command != null && command.getNewPassword() != null
                && !command.getNewPassword().equals(command.getNewPasswordConfirmation())) {
            errors.rejectValue("newPasswordConfirmation",
                    "error.passwordEdit.newPasswordConfirmationDoesNotMatch");
        }
        if(!errors.hasErrors()) {
            if(!securityService.validatePasswordForCurrentUser(command.getCurrentPassword())) {
                errors.rejectValue("currentPassword", "error.passwordEdit.currentPasswordIncorrect");
            }
        }
    }

}
