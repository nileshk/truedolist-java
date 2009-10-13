package com.myconnector.domain;

import java.io.Serializable;
import java.util.Date;

public class UserCookie implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private UserData userData;
    private Date createDate;

    public UserCookie() {
        super();
    }

    public UserCookie(String id) {
        super();
        this.id = id;
    }

    public UserCookie(String id, Date createDate) {
        super();
        this.id = id;
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (this == obj)
            return true;

        final UserCookie cookie = (UserCookie) obj;

        if (!cookie.getId().equals(getId()))
            return false;
        if (!cookie.getCreateDate().equals(getCreateDate()))
            return false;

        return true;
    }

    public int hashCode() {
        String hashStr = this.getClass().getName() + ":" + getId().hashCode()
                + getCreateDate().hashCode();
        return hashStr.hashCode();
    }

}
