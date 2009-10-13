package com.myconnector.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.myconnector.util.CommonThreadLocal;
import com.myconnector.util.HttpSessionThreadLocal;
import com.myconnector.web.controller.LoginController;

public class HttpSessionServletFilter implements Filter {

    static Logger logger = Logger.getLogger(HttpSessionServletFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest) {
                final HttpServletRequest httpRequest = (HttpServletRequest) request;
                final HttpSession session = httpRequest.getSession();
                HttpSessionThreadLocal.set(session);
                processCookies(httpRequest);
            } else {
                HttpSessionThreadLocal.clear();
            }
            chain.doFilter(request, response);
        } catch (IOException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            throw ex;
        } catch (ServletException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            throw ex;
        } catch (RuntimeException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            throw ex;

        } finally {
            // Clear out session once request is finished
            HttpSessionThreadLocal.clear();
            CommonThreadLocal.clear();
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    private void processCookies(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginController.USER_COOKIE_NAME)) {
                    String cookieValue = cookie.getValue();
                    if (cookieValue != null && !cookieValue.equals("")) {
                        logger.debug("HttpSessionServletFilter: CookieValue=" + cookieValue);
                        CommonThreadLocal.setCookieValue(cookieValue);
                    }
                    break;
                }
            }
        }
    }

}
