package com.myconnector.service.gwt;

import org.apache.log4j.Logger;

import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.exception.MessageException;

public class AbstractBaseServiceGwtImpl {

    static Logger logger = Logger.getLogger(AbstractBaseServiceGwtImpl.class);
    
    protected RuntimeException handleException(Exception e) {
        if(logger.isDebugEnabled()) {
            logger.debug("Exception", e);
        }
        if (e instanceof MessageException) {
        	MessageException messageException = (MessageException) e;
            ClientMessageException cex = new ClientMessageException(e);
            cex.setCode(messageException.getCode());
            cex.setDefaultMessage(messageException.getMessage());
            return cex;
        } else {
            ClientUnexpectedException cex = new ClientUnexpectedException(e);
            cex.setMessage(e.getMessage());
            return cex;
        }
    }

}
