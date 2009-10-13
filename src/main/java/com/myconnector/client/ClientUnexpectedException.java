package com.myconnector.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientUnexpectedException extends RuntimeException implements IsSerializable {

    private static final long serialVersionUID = 4133372233126437611L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ClientUnexpectedException() {
    }
    
    public ClientUnexpectedException(Throwable cause) {
        super(cause);
    }
}
