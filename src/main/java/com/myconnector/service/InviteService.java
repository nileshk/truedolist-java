package com.myconnector.service;

import com.myconnector.domain.Invite;

public interface InviteService extends GenericService<Invite, String> {

	/**
	 * Create a new invite
	 * 
	 * @return Token for the newly created invite
	 */
	String createNewInvite();
	
	String createNewInvite(String email);

	Invite getInviteByToken(String token);
}
