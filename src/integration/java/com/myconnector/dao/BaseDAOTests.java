package com.myconnector.dao;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import util.TestData;

public class BaseDAOTests extends AbstractTransactionalDataSourceSpringContextTests {

    protected String[] getConfigLocations() {
        return TestData.getApplicationContextFileList();
    }
    
    protected void print(String str) {
        System.out.println(str);
    }

}
