package com.myconnector.service;

import org.apache.log4j.Logger;

import com.myconnector.dao.EmailVerificationDAO;
import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.EmailVerification;
import com.myconnector.domain.UserData;
import com.myconnector.exception.MessageException;

public class EmailVerificationServiceImpl implements EmailVerificationService {

	static Logger logger = Logger.getLogger(EmailVerificationServiceImpl.class);
	
	private EmailVerificationDAO emailVerificationDAO;
	private UserDataDAO userDataDAO;
	private EmailService emailService;
	
	public void setEmailVerificationDAO(EmailVerificationDAO emailVerificationDAO) {
		this.emailVerificationDAO = emailVerificationDAO;
	}
	
	public void setUserDataDAO(UserDataDAO userDataDAO) {
		this.userDataDAO = userDataDAO;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public UserData verifyEmail(String token) {
		logger.debug("Verifying token: " + token);
		EmailVerification verification = emailVerificationDAO.load(token);
		if (verification == null) {
			throw new MessageException("error.emailVerification.invalidToken",
					"Invalid email verification token");
		}
		UserData user = verification.getUserData();
		boolean saveUserData = false;
		if(verification.getEmail() != null && verification.getEmail().length() > 0) {
			user.setEmail(verification.getEmail());
			saveUserData = true;
		}
		if(!verification.getUserData().getEnabled()) {
			user.setEnabled(Boolean.TRUE);
			saveUserData = true;
		}
		if(saveUserData) {
			userDataDAO.save(user);
		}
		emailService.sendEmailVerificationConfirmation(verification);
		emailVerificationDAO.delete(verification);
		return user;
	}
}
