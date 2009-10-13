package com.myconnector.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.myconnector.service.InviteService;

public class CreateInviteController implements Controller {

	private InviteService inviteService;
	private String view;

	public void setInviteService(InviteService inviteService) {
		this.inviteService = inviteService;
	}

	public void setView(String view) {
		this.view = view;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (request.getParameter("submit.createInvite") != null) {
			String email = request.getParameter("email");
			String token = inviteService.createNewInvite(email);
			map.put("token", token);
			map.put("email", email);
		}
		return new ModelAndView(view, map);
	}

}
