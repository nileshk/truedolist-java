package com.myconnector.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.exception.MessageException;
import com.myconnector.service.EmailVerificationService;

public class EmailVerificationController implements Controller {

	private String view;
	private EmailVerificationService emailVerificationService;
	
	public void setView(String view) {
		this.view = view;
	}

	public void setEmailVerificationService(EmailVerificationService emailVerificationService) {
		this.emailVerificationService = emailVerificationService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String token = request.getParameter("token");
		if(token == null || "".equals(token)) {
			throw new MessageException("error.emailVerification.noTokenProvided");
		}
		emailVerificationService.verifyEmail(token);
		return new ModelAndView(view, null);
		
		
	}

}
