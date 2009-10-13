package com.myconnector.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.myconnector.dao.EmailVerificationDAO;
import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.EmailVerification;
import com.myconnector.domain.UserData;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;
import com.myconnector.util.TokenUtil;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class UserServiceImpl implements UserService {

	static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
    private UserDataDAO userDataDAO;
    private SecurityService securityService;
    private EmailVerificationDAO emailVerificationDAO;
    private EmailService emailService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setUserDataDAO(UserDataDAO userDataDAO) {
        this.userDataDAO = userDataDAO;
    }
    
    public void setEmailVerificationDAO(EmailVerificationDAO emailVerificationDAO) {
		this.emailVerificationDAO = emailVerificationDAO;
	}
    
    public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

    public void deleteUser(UserData userData) {
        userDataDAO.delete(userData);
    }

    public List<UserData> getList() {
        return userDataDAO.getList();
    }

    public UserData getUserByUserLogin(String userLogin) {
        return userDataDAO.getUserByUserLogin(userLogin);
    }

    public UserData getUserById(Long id) {
        UserData userData = userDataDAO.load(id);
        return userData;
    }

    public void saveUser(UserData userData) {
        String encryptedHashedPassword = securityService.createPasswordString(userData.getUserPassword());
        userData.setUserPassword(encryptedHashedPassword);
        userDataDAO.save(userData);
    }

    public void updateUser(UserData userData) {
        userDataDAO.update(userData);
    }

    public void deleteById(Long id) {
        userDataDAO.deleteById(id);
    }

	public void changeEmail(String newEmail) {
		UserData user = getCurrentUser();
		if(user != null) {
			EmailVerification verification = user.createEmailVerification();
			verification.setEmail(newEmail);
			emailVerificationDAO.save(verification);
			emailService.sendEmailVerification(verification);
		} else {
			throw new MessageException("user.notLoggedIn");
		}
	}
	
	private UserData getCurrentUser() {
        return userDataDAO.load(CommonThreadLocal.getUserId());
	}

}