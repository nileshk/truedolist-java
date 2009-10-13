package com.myconnector.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserData implements Serializable {

	private static final long serialVersionUID = 4452166825558810161L;
	
	private Long id;
	private String userLogin;
	private String userPassword;
	private byte securityLevel;
	private String email;
	private Boolean enabled = Boolean.FALSE;
	private Set<UserCookie> cookies;
	private Set<TodoList> todoLists;
	private Set<EmailVerification> emailVerifications;
	
	public UserData() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UserData(Long id) {
		this.id = id;
	}

	/**
	 * Constructor for required fields
	 */
	public UserData(Long id, String userLogin, String userPassword,
			byte securityLevel) {
		this.id = id;
		this.userLogin = userLogin;
		this.userPassword = userPassword;
		this.securityLevel = securityLevel;
	}

	public UserCookie getNewCookie() {
		UserCookie newCookie = new UserCookie();
		newCookie.setCreateDate(new Date());
		addCookie(newCookie);
		newCookie.setUserData(this);
		return newCookie;
	}
	
	public void addCookie(UserCookie cookie) {
		if (null == getCookies())
			setCookies(new HashSet<UserCookie>());
		getCookies().add(cookie);
	}

	public EmailVerification createEmailVerification() {
		EmailVerification newEmailVerification = new EmailVerification();
		newEmailVerification.setCreateDate(new Date());
		//addEmailVerification(newEmailVerification);
		newEmailVerification.setUserData(this);
		return newEmailVerification;
	}
	
	public void addEmailVerification(EmailVerification emailVerification) {
		if (null == getEmailVerifications()) {
			setEmailVerifications(new HashSet<EmailVerification>());
		}
		getEmailVerifications().add(emailVerification);
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.myconnector.domain.UserData)) return false;
		else {
			com.myconnector.domain.UserData userData = (com.myconnector.domain.UserData) obj;
			if (null == this.getId() || null == userData.getId()) return false;
			else return (this.getId().equals(userData.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	

	protected int hashCode = Integer.MIN_VALUE;


	/**
	 * @hibernate.id generator-class="uuid.hex" column="id"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: user_login
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * Set the value related to the column: user_login
	 * 
	 * @param userLogin
	 *            the user_login value
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * Return the value associated with the column: user_password
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * Set the value related to the column: user_password
	 * 
	 * @param userPassword
	 *            the user_password value
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * Return the value associated with the column: security_level
	 */
	public byte getSecurityLevel() {
		return securityLevel;
	}

	/**
	 * Set the value related to the column: security_level
	 * 
	 * @param securityLevel
	 *            the security_level value
	 */
	public void setSecurityLevel(byte securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public Set<UserCookie> getCookies() {
        return cookies;
    }

    public void setCookies(Set<UserCookie> cookies) {
        this.cookies = cookies;
    }
    
	public Set<TodoList> getTodoLists() {
		return todoLists;
	}

	public void setTodoLists(Set<TodoList> todoLists) {
		this.todoLists = todoLists;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<EmailVerification> getEmailVerifications() {
		return emailVerifications;
	}
	
	public void setEmailVerifications(Set<EmailVerification> emailVerifications) {
		this.emailVerifications = emailVerifications;
	}
}