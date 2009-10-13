package com.myconnector.client.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.myconnector.client.domain.interfaces.HasId;

public class UserDataClient implements IsSerializable, HasId {

	private Long id;
	private String username;
	private String email;

	// private String securityLevel;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
