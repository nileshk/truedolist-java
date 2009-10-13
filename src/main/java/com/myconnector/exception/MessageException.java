package com.myconnector.exception;

import org.apache.log4j.Logger;

/**
 * This exception is used when an error needs to be passed to the user
 * interface. It is instantiated with a message code which is translated later
 * from the value in the resource bundle (e.g. Messages.properties)
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class MessageException extends RuntimeException {

    static Logger logger = Logger.getLogger(MessageException.class);
    
    private static final long serialVersionUID = 1L;

    private String code;
    private String defaultMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public MessageException(String code) {
        this.code = code;
        logger.debug("code = " + code);
    }

    public MessageException(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        logger.debug("code = " + code);
        logger.debug("defaultMessage = " + defaultMessage);
    }

}
