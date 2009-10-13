package com.myconnector.domain;

import java.io.Serializable;
import java.util.Date;

import com.myconnector.domain.interfaces.HasUserData;

public class EmailVerification implements Serializable, HasUserData {

	private static final long serialVersionUID = 1L;

	private String id;
	private String email;
	private UserData userData;
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (this == obj)
            return true;

        final EmailVerification ev = (EmailVerification) obj;

        if (!ev.getId().equals(getId()))
            return false;
        if (!ev.getCreateDate().equals(getCreateDate()))
            return false;

        return true;
    }

    public int hashCode() {
        String hashStr = this.getClass().getName() + ":" + getId().hashCode()
                + getCreateDate().hashCode();
        return hashStr.hashCode();
    }
    
}
