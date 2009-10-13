package com.myconnector.service.gwt;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.client.domain.UserDataClient;
import com.myconnector.client.service.UserServiceGwt;
import com.myconnector.domain.UserData;
import com.myconnector.exception.MessageException;
import com.myconnector.gwt.GwtRpcEndPoint;
import com.myconnector.service.SecurityService;
import com.myconnector.service.UserService;

@Controller
@GwtRpcEndPoint
public class UserServiceGwtImpl extends AbstractBaseServiceGwtImpl implements UserServiceGwt {

	static Logger logger = Logger.getLogger(UserServiceGwtImpl.class);

	private SecurityService securityService;
	private UserService userService;

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDataClient loginCheckWithCookie(String cookieValue) {
		try {
			UserData userData = securityService.loginCheckWithCookie(cookieValue);
			if (userData == null) {
				return null;
			}
			UserDataClient userDataClient = new UserDataClient();
			userDataClient.setId(userData.getId());
			userDataClient.setUsername(userData.getUserLogin());
			userDataClient.setEmail(userData.getEmail());
			return userDataClient;
		} catch (Exception e) {
			throw handleException(e);
		}

	}

	public Boolean checkIfLoggedIn(String cookieValue) throws ClientMessageException,
			ClientUnexpectedException {
		try {
			logger.debug("checkIfLoggedIn");
			UserData userData = securityService.loginCheckWithCookie(cookieValue);
			if (userData == null) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void changePassword(String currentPassword, String newPassword) {
		try {
			// TODO do we want to make a method in SecurityService for this instead?
			if (securityService.validatePasswordForCurrentUser(currentPassword)) {
				securityService.changePasswordForCurrentUser(newPassword);
			} else {
				throw new MessageException("error.passwordEdit.currentPasswordIncorrect");
			}
		} catch (Exception e) {
			throw handleException(e);
		}
	}

	public void changeEmail(String newEmail) {
		try {
			userService.changeEmail(newEmail);
		} catch (Exception e) {
			throw handleException(e);
		}
	}

}
