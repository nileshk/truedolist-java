package com.myconnector.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.myconnector.dao.AbstractDAO;
import com.myconnector.dao.InviteDAO;
import com.myconnector.domain.Invite;
import com.myconnector.util.TokenUtil;

public class InviteServiceImpl extends GenericServiceImpl<Invite, String> implements InviteService {

	static Logger logger = Logger.getLogger(InviteServiceImpl.class); 
	
	private InviteDAO inviteDAO;
	private MailSender mailSender;
	private SimpleMailMessage messageTemplate;

	public void setInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessageTemplate(SimpleMailMessage messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	@Override
	protected AbstractDAO<Invite, String> getGenericDAO() {
		return inviteDAO;
	}

	public String createNewInvite() {
		return createNewInvite(null);
	}

	public String createNewInvite(String email) {
		String token = TokenUtil.getToken();
		Invite invite = new Invite();
		invite.setToken(token);
		if (email != null && email.length() > 0) {
			invite.setEmail(email);
			sendEmail(token, email);
		}
		this.save(invite);
		return token;
	}

	public Invite getInviteByToken(String token) {
		return inviteDAO.getInviteByToken(token);
	}

	public void sendEmail(String token, String toAddress) {
		if(logger.isDebugEnabled()) {
			logger.debug("Sending invite to email address: " + toAddress + " with token: " + token);
		}
		SimpleMailMessage message = new SimpleMailMessage(messageTemplate);
		message.setTo(toAddress);
		StringBuilder sb = new StringBuilder();
		sb.append("You have been invited to use True Do List.  ");
		sb.append("Visit the following URL to sign up: \n");
		sb.append("\n");
		sb.append("http://www.truedolist.com/invite.do?token=");
		sb.append(token);
		message.setText(sb.toString());
		mailSender.send(message);
	}

}
