package com.myconnector.webservice;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.myconnector.domain.UserData;
import com.myconnector.service.SecurityService;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class BaseWSImpl implements BaseWS {

    static Logger logger = Logger.getLogger(BaseWSImpl.class);

    protected ApplicationContext ctx; // TODO remove this
    protected SecurityService securityService;

    // TODO XFire Session handling
    
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    protected void onInit() {
//        super.onInit();
//        ctx = getApplicationContext();
//        HttpSessionThreadLocal
//        .set(getServletEndpointContext().getHttpSession());        
    }
    
    protected HttpSession getHttpSession() {
        return null; // TODO        
    }

    protected SecurityService getSecurityService() {
        return securityService;
    }

    protected RuntimeException handleException(Exception ex) {
        logException(ex);
        if(ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        } else {
            return new RuntimeException(ex);
        }
    }

    protected void logException(Exception ex) {
        logger.error("Exception", ex);
    }
    
    
    public boolean login(String username, String password) {
        try {
            UserData user = getSecurityService().login(username, password);
//            logger.debug("userId: " + (String)getHttpSession().getAttribute("userId"));
//            logger.debug("username: " + (String)getHttpSession().getAttribute("username"));
            return user != null;
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    public void logout() {
        try {
            getSecurityService().logout();
        } catch (Exception ex) {
            throw handleException(ex);
        }        
    }


    public String getLoggedUsername() {
        try {
            return getSecurityService().getCurrentUser();
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    public String getHttpSessionId() {
        try {
            return getHttpSession().getId();
        } catch(Exception ex) {
            throw handleException(ex);
        }
    }

}