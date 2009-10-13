package com.myconnector.util;

/**
 * Interface for hashing passwords for storage in database
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface DbPasswordEncoder {

	/**
	 * @param plainTextPassword
	 * @param salt
	 * @return Combination of the salt and hash for direct storage in DB
	 */
	String encodePassword(String plainTextPassword);

	/**
	 * 
	 * @param saltedHashFromDb
	 *            Combination of hash and salt directly loaded from DB
	 * @return
	 */
	boolean isPasswordValid(String plaintTextPassword, String saltedHashFromDb);

}
