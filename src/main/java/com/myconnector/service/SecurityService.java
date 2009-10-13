/*
 * Created on Sep 5, 2004
 *
 */
package com.myconnector.service;

import com.myconnector.domain.UserCookie;
import com.myconnector.domain.UserData;

/**
 * 
 * {@link SecurityServiceImpl}
 * 
 * @author Nil
 */
public interface SecurityService {
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return UserData object if login was successful, null otherwise
	 */
	public UserData login(String username, String password);

	public void logout();

	public void logout(String cookieValue);

	/**
	 * Perform login check, and re-populate CommonThreadLocal from HttpSession
	 * if necessary
	 * 
	 * XXX Maybe we shouldn't use CommonThreadLocal and just use HttpSession
	 * exclusively
	 * 
	 * @return
	 */
	public boolean loginCheck();

	public String getCurrentUser();

	/**
	 * Initialize a virgin database
	 * 
	 * @return Return admin user if it is a virgin database, return null if it
	 *         is not a virgin database
	 */
	public UserData initialize();

	public UserCookie getCookie(Long userId);

	public UserData loginWithCookie(String cookieValue);

	/**
	 * Check to see if we are logged in.  If not, try to use cookie to login.
	 * @param cookieValue
	 * @return
	 */
	public UserData loginCheckWithCookie(String cookieValue);
	
	/**
	 * Validate whether a password is the correct password for a user
	 * 
	 * @param password
	 *            Password to validate
	 * @return True if supplied password is the password of the current user,
	 *         false otherwise
	 */
	public boolean validatePasswordForCurrentUser(String password);

	public boolean isExistingUser(String userName);
	
	/**
	 * Change password for current user. WARNING: there are no validations for
	 * this, so use with caution
	 * 
	 * @param newPassword
	 */
	public void changePasswordForCurrentUser(String newPassword);

	public String createPasswordString(String plaintextPassword);

	public void createNewUserWithInvite(String inviteToken, String userName,
			String email, String password);
}