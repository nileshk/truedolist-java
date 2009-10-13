package com.myconnector.service;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.myconnector.domain.EmailVerification;

public class EmailServiceImpl implements EmailService {

	static Logger logger = Logger.getLogger(EmailServiceImpl.class);

	private MailSender mailSender;
	private String fromAddress = "noreply@truedolist.com";

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	private SimpleMailMessage createNewMessage(String subject, String text, String toAddress) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromAddress);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		simpleMailMessage.setTo(toAddress);
		return simpleMailMessage;
	}

	@Override
	public void sendEmailVerification(EmailVerification verification) {
		if (logger.isDebugEnabled()) {
			logger.debug("Sending e-mail verification e-mail with token: " + verification.getId());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Please verify this e-mail address by visiting the following URL:\n");
		sb.append("\n");
		sb.append("http://www.truedolist.com/emailVerification.do?token=");
		sb.append(verification.getId());
		mailSender.send(createNewMessage("Verify your e-mail address", sb.toString(), verification
				.getEmail()));
	}

	@Override
	public void sendEmailVerificationConfirmation(EmailVerification verification) {
		logger.debug("Sending e-mail verification confirmation");
		StringBuilder sb = new StringBuilder();
		sb.append(verification.getUserData().getUserLogin());
		sb.append(",\n\n");
		sb.append("Your e-mail address has been successfully verified");
		mailSender.send(createNewMessage("E-mail verified successfully", sb.toString(),
				verification.getEmail()));
	}

}
