package com.myconnector.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.myconnector.dao.EmailVerificationDAO;
import com.myconnector.dao.InviteDAO;
import com.myconnector.dao.UserCookieDAO;
import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.EmailVerification;
import com.myconnector.domain.Invite;
import com.myconnector.domain.UserCookie;
import com.myconnector.domain.UserData;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;
import com.myconnector.util.DbPasswordEncoder;
import com.myconnector.util.HttpSessionThreadLocal;
import com.myconnector.util.TokenUtil;
import com.myconnector.web.controller.LoginController;

/**
 * 
 * @author Nil
 */
public class SecurityServiceImpl implements SecurityService {

    public static final String COOKIE_SPLITTER = "_";

    private Logger logger = Logger.getLogger(SecurityServiceImpl.class);

    private UserDataDAO userDataDAO;
    private UserCookieDAO userCookieDAO;
    private InviteDAO inviteDAO;
    private DbPasswordEncoder dbPasswordEncoder;
    private EmailService emailService;
    private EmailVerificationDAO emailVerificationDAO;

    public void setDbPasswordEncoder(DbPasswordEncoder dbPasswordEncoder) {
		this.dbPasswordEncoder = dbPasswordEncoder;
	}
    
    public void setUserDataDAO(UserDataDAO userDataDAO) {
        this.userDataDAO = userDataDAO;
    }

    public void setUserCookieDAO(UserCookieDAO userCookieDAO) {
        this.userCookieDAO = userCookieDAO;
    }

    public void setInviteDAO(InviteDAO inviteDAO) {
        this.inviteDAO = inviteDAO;
    }

    public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
    
    public void setEmailVerificationDAO(EmailVerificationDAO emailVerificationDAO) {
		this.emailVerificationDAO = emailVerificationDAO;
	}
    
    /*
     * @see com.myconnector.service.SecurityService#login(java.lang.String,
     *      java.lang.String)
     */
    public UserData login(String username, String password) {
        UserData userData = userDataDAO.getUserByUserLogin(username);
        if (userData != null) {
            if (validatePassword(userData.getUserPassword(), password)) {
                logger.debug("Logging in as " + userData.getUserLogin());
                HttpSessionThreadLocal.setAttribute(LoginController.USER_ID, userData.getId());
                HttpSessionThreadLocal.setAttribute(LoginController.USER_NAME, username);
                return userData;
            }
        }
        return null;
    }

    public void logout() {
        logout(null);
    }

    /*
     * @see com.myconnector.service.SecurityService#logout()
     */
    public void logout(String cookieValue) {

        if (cookieValue != null && !cookieValue.equals("")) {
            String[] cookieValueSplit = cookieValue.split(COOKIE_SPLITTER);
            if (cookieValueSplit != null && cookieValueSplit.length == 2) {
                try {
                    String cookieId = cookieValueSplit[0];
                    String userId = cookieValueSplit[1];
                    UserCookie cookie = userCookieDAO.load(cookieId);
                    if (cookie != null) {
                        if (!cookie.getUserData().getId().equals(userId)) {
                            logger
                                    .fatal("Deleting a cookie whose userId doesn't match actual user in database");
                        }
                        userCookieDAO.delete(cookie);
                    }
                } catch (HibernateObjectRetrievalFailureException ex) {
                    logger
                            .warn("Attempted to fetch a cookie for deletion that doesn't exist in database");
                    logger.warn("cookieValue = " + cookieValue);
                    logger.debug(ex);
                }
            } else {
                logger.debug("cookie didn't split, value = " + cookieValue);
            }
        }
        HttpSessionThreadLocal.clear();
    }

    /*
     * @see com.myconnector.service.SecurityService#getCurrentUser()
     */
    public String getCurrentUser() {
        // TODO check HttpSession, etc.
        String username = CommonThreadLocal.getUsername();
        logger.debug("SecurityServiceImpl.getCurrentUser: " + username);
        return username;
    }

    /*
     * @see com.myconnector.service.SecurityService#initialize()
     */
    public UserData initialize() {
        if (userDataDAO.getList().isEmpty()) {
            UserData userData = new UserData();
            userData.setUserLogin("admin");
            userData.setUserPassword(dbPasswordEncoder.encodePassword("admin"));
            logger.debug("Setting security level 100.");
            userData.setSecurityLevel((byte) 100);
            userData.setEmail("root@localhost");
            userDataDAO.save(userData);
            return userData;
        } else {
            return null;
        }
    }

    public boolean loginCheck() {
        String username = getCurrentUser();
		return (username != null && username.length() > 0);
    }

    public UserCookie getCookie(Long userId) {
        UserData userData = userDataDAO.load(userId);
        UserCookie cookie = new UserCookie();
        // TODO assign id with a less predictable value
        cookie.setUserData(userData);
        cookie.setCreateDate(new Date());
        userCookieDAO.save(cookie);
        logger.debug("cookie id: " + cookie.getId());
        return cookie;
    }

    public UserData loginWithCookie(String cookieValue) {
        // XXX this method name might be misleading because it doesn't appear to do the full login
        logger.debug("Attempting login with cookie value: " + cookieValue);
        String[] cookieValueSplit = cookieValue.split(COOKIE_SPLITTER);
        if (cookieValueSplit == null || cookieValueSplit.length < 2) {
            return null;
        }
        String cookieId = cookieValueSplit[0];
        Long userId = Long.valueOf(cookieValueSplit[1]);
        UserCookie cookie = null;
        try {
            cookie = userCookieDAO.load(cookieId);
        } catch (HibernateObjectRetrievalFailureException ex) {
            logger.debug("Cookie not in database.");
            logger.debug(ex);
            return null;
        }
        if (cookie == null) {
            return null;
        }
        UserData user = cookie.getUserData();
        if (!user.getId().equals(userId)) {
            logger.fatal("POTENTIAL HACK ATTEMPT: FORGED COOKIE DOES NOT MATCH USER");
            logger.fatal("cookieValue: " + cookieValue);
            logger.fatal("cookie owner: " + user.getUserLogin());
            return null;
        }

        return user;
    }

    private boolean validatePassword(String saltedHashFromDb, String suppliedPassword) {
    	return dbPasswordEncoder.isPasswordValid(suppliedPassword, saltedHashFromDb);
    }

    public boolean validatePasswordForCurrentUser(String password) {
        if (password == null || password.equals("")) {
            return false;
        }
        UserData user = userDataDAO.load(CommonThreadLocal.getUserId());
        return validatePassword(user.getUserPassword(), password);
    }

    public void changePasswordForCurrentUser(String newPassword) {
        UserData user = userDataDAO.load(CommonThreadLocal.getUserId());
        user.setUserPassword(createPasswordString(newPassword));
    }

    public String createPasswordString(String plainTextPassword) {
    	return dbPasswordEncoder.encodePassword(plainTextPassword);
    }

    public void createNewUserWithInvite(String inviteToken, String userName, String email,
            String password) {
        Invite invite = inviteDAO.getInviteByToken(inviteToken);
        if (invite == null) {
            throw new MessageException("error.createNewUser.invalidToken");
        }
        inviteDAO.delete(invite);
        UserData userData = new UserData();
        userData.setUserLogin(userName);
        userData.setUserPassword(createPasswordString(password));
        // TODO decide on default security level
        userData.setSecurityLevel((byte) 50);
        userData.setEmail(email);
        /*
         * If invite e-mail is empty or different than the e-mail provided in the form, require that
         * the e-mail be verified before the account is verified
         */
        if (invite.getEmail() == null || "".equals(invite.getEmail()) || !invite.equals(email)) {
			userData.setEnabled(Boolean.FALSE);
			EmailVerification verification = userData.createEmailVerification();
			verification.setEmail(email);
			emailVerificationDAO.save(verification);
			emailService.sendEmailVerification(verification);
		} else {
			userData.setEnabled(Boolean.TRUE);
		}
        userDataDAO.save(userData);
    }

    public boolean isExistingUser(String userName) {
        return userDataDAO.getUserByUserLogin(userName) != null;
    }

    public UserData loginCheckWithCookie(String cookieValue) {
        if (loginCheck()) {
            Long userId = HttpSessionThreadLocal.getUserId();
            if (userId != null) {
                try {
                    return userDataDAO.load(userId);
                } catch (ObjectNotFoundException ex) {
                    logger.debug("ObjectNotFoundException", ex);
                    return null;
                }
            } else {
                return null;
            }
        } else if (cookieValue != null && cookieValue.length() > 0) {
            logger.debug("User not in session, attempting login with cookie");
            UserData userData = loginWithCookie(cookieValue);
            saveUserDataToSession(userData);
            return userData;
        }
        return null;
    }

    private void saveUserDataToSession(UserData userData) {
        HttpSessionThreadLocal.setAttribute(LoginController.USER_ID, userData.getId());
        HttpSessionThreadLocal.setAttribute(LoginController.USER_NAME, userData.getUserLogin());
        HttpSessionThreadLocal.setAttribute(LoginController.USER_SECURITY_LEVEL, userData
                .getSecurityLevel());
    }

}