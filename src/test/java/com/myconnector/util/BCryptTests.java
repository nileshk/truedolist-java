package com.myconnector.util;

import junit.framework.TestCase;

public class BCryptTests extends TestCase {

	public void test() {
		
		String salt = BCrypt.gensalt();
		System.out.println("salt: " + salt);
		System.out.println("salt length: " + salt.length());
		String hashed = BCrypt.hashpw("mypassword", salt);
		System.out.println("hash: " + hashed);
		System.out.println("length: " + hashed.length());		
		assertTrue(BCrypt.checkpw("mypassword", hashed));
	}
	
	public void testGeneratingSalts1to100() {
		for (int i = 1; i < 99; i++) {
			System.out.println("salt strength:" + i);
			String salt = BCrypt.gensalt(i);
			System.out.println("salt: " + salt);
			System.out.println("salt length: " + salt.length());
			assertEquals(29, salt.length());
		}
	}

	public void testGeneratingSalts100to1000() {
		for (int i = 100; i < 1000; i++) {
			System.out.println("salt strength:" + i);
			String salt = BCrypt.gensalt(i);
			System.out.println("salt: " + salt);
			System.out.println("salt length: " + salt.length());
			assertEquals(30, salt.length());
		}
	}
	
}
