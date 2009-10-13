package com.myconnector.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserCookieCheck {

    public boolean checkForUserCookie(HttpServletRequest request, HttpServletResponse response);
    
}
