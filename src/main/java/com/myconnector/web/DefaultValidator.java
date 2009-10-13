/*
 * Created on Sep 1, 2004
 *
 */
package com.myconnector.web;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/** 
 *
 * @author nil
 */
public class DefaultValidator implements Validator {

    static Logger logger = Logger.getLogger(DefaultValidator.class);
    
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class arg0) {
        return(true);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}
