package com.myconnector.webservice;

import org.apache.log4j.Logger;

public class TestServiceImpl implements TestService {

    static Logger logger = Logger.getLogger(TestServiceImpl.class);
    
    public String reverseString(String str) {
        logger.debug("starting reverseString");
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));            
        }
        logger.debug("ending reverseString");
        return sb.toString();
    }

    public void throwException() {
        throw new RuntimeException("test exception");
    }

}
