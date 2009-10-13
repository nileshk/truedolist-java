package com.myconnector.domain;

import java.io.Serializable;

import com.myconnector.domain.interfaces.HasId;

public class Invite implements Serializable, HasId {

	private static final long serialVersionUID = 1L;
	protected int hashCode = Integer.MIN_VALUE;
	
	private Long id;
	private String token;
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Invite))
            return false;
        else {
            Invite todo = (Invite) obj;
            if (null == this.getId() || null == todo.getId())
                return false;
            else
                return (this.getId().equals(todo.getId()));
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }	
	
}
