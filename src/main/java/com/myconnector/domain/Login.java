/*
 * Created on Sep 5, 2004
 *
 */
package com.myconnector.domain;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class Login {

    private String username;
    private String password;
    private Boolean remember;

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
