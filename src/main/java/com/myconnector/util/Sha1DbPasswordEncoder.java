package com.myconnector.util;

import java.util.UUID;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

public class Sha1DbPasswordEncoder implements DbPasswordEncoder {

	private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();

	public String encodePassword(String plainTextPassword) {
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		// We only need a 24 byte salt (without dashes), for now
		String salt = token.replace("-", "").substring(0, 24);
		String encryptedPassword = passwordEncoder.encodePassword(plainTextPassword, salt);
		return encryptedPassword + salt;
	}

	public boolean isPasswordValid(String plaintTextPassword, String saltedHashFromDb) {
		// Password is first 40 bytes
		String encryptedPassword = saltedHashFromDb.substring(0, 40);
		// Hash is last 24 bytes
		String hash = saltedHashFromDb.substring(40);
		String encryptedSuppliedPassword = passwordEncoder.encodePassword(plaintTextPassword, hash);
		return encryptedPassword.equals(encryptedSuppliedPassword);
	}

}
