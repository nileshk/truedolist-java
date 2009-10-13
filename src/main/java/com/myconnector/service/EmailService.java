package com.myconnector.service;

import com.myconnector.domain.EmailVerification;

public interface EmailService {
	
	void sendEmailVerification(EmailVerification verification);
	
	void sendEmailVerificationConfirmation(EmailVerification verification);
}
