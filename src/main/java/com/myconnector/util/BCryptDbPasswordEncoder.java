package com.myconnector.util;

public class BCryptDbPasswordEncoder implements DbPasswordEncoder {

	public String encodePassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public boolean isPasswordValid(String plaintTextPassword, String saltedHashFromDb) {
		return BCrypt.checkpw(plaintTextPassword, saltedHashFromDb);
	}

}
