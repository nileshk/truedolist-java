package com.myconnector.domain;

import org.hibernate.id.IdentifierGenerator;

import junit.framework.TestCase;

public class CookieIdGeneratorTests extends TestCase {

	IdentifierGenerator generator;
	
	@Override
	protected void setUp() throws Exception {
		generator = new CookieIdGenerator();
		super.setUp();
	}
	
	public void testGeneratorId() {
		String id = (String) generator.generate(null, null);
		assertEquals(32, id.length());
		// XXX Verify characters are all hex
	}
	
}
