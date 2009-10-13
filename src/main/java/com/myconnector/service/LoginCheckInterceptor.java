package com.myconnector.service;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.myconnector.domain.UserData;
import com.myconnector.exception.MessageException;
import com.myconnector.util.CommonThreadLocal;

public class LoginCheckInterceptor implements MethodBeforeAdvice {

    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void before(Method method, Object[] args, Object target) throws Throwable {
        UserData userData = securityService
                .loginCheckWithCookie(CommonThreadLocal.getCookieValue());
        if (userData == null) {
            throw new MessageException("user.notLoggedIn");
        }
    }

}
