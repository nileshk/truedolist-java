package com.myconnector.webservice;

import junit.framework.TestCase;

public class TestServiceImplTests extends TestCase {

    public void testReverseString() {
        TestService testService = new TestServiceImpl();
        String ret = testService.reverseString("ABC");
        assertEquals("CBA", ret);
    }
    
}
