package com.myconnector.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientMessageException extends RuntimeException implements IsSerializable {

    private static final long serialVersionUID = 210740130634573281L;
    
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

    public ClientMessageException() {
        
    }
    
    public ClientMessageException(String code) {
        this.code = code;
    }

    public ClientMessageException(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    
    public ClientMessageException(Throwable cause) {
        super(cause);
    }


}
