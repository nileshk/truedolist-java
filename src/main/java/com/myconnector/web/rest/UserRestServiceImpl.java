package com.myconnector.web.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.myconnector.domain.UserData;
import com.myconnector.service.SecurityService;
import com.myconnector.util.CommonThreadLocal;
import com.myconnector.xml.user.LoginParams;

@Path("/user/")
@ProduceMime("application/xml")
public class UserRestServiceImpl {

	static Logger logger = Logger.getLogger(UserRestServiceImpl.class);

	private SecurityService securityService;

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@GET
	@Path("/connect")
	@ProduceMime("text/plain")
	public String checkLogin() {
        UserData userData = securityService
        .loginCheckWithCookie(CommonThreadLocal.getCookieValue());
		if (userData == null) {
		    return "NOTLOGGEDIN";
		}
		return "OK";
	}
	
	@POST
	@Path("/connect")
	public Response connect(LoginParams login) {
		if (logger.isDebugEnabled() && login != null) {
			logger.debug("Attempting to login as user " + login.getUsername());
		}
		securityService.login(login.getUsername(), login.getPassword());
		return Response.status(Status.CREATED).build();
	}

	@DELETE
	@Path("/connect")
	public Response disconnect() {
		securityService.logout();
		return Response.status(Status.OK).build();
	}

}
