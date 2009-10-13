package com.myconnector.dao;

import com.myconnector.domain.Invite;

public interface InviteDAO extends AbstractDAO<Invite, String> {
	
	public Invite getInviteByToken(String token);
	
}
