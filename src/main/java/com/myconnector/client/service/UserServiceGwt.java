package com.myconnector.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.myconnector.client.ClientMessageException;
import com.myconnector.client.ClientUnexpectedException;
import com.myconnector.client.domain.UserDataClient;

public interface UserServiceGwt extends RemoteService {

    /**
     * Method to check if user is logged in. If they are not, attempt to login
     * with cookie.
     * 
     * @param cookieValue
     * @return
     * @throws ClientMessageException
     * @throws ClientUnexpectedException
     */
    UserDataClient loginCheckWithCookie(String cookieValue) throws ClientMessageException,
            ClientUnexpectedException;

    /**
     * Does same thing as loginCheckWithCookie excepts only returns Boolean to
     * tell us whether we are logged in or not.
     * 
     * @param cookieValue
     * @return
     * @throws ClientMessageException
     * @throws ClientUnexpectedException
     */
    Boolean checkIfLoggedIn(String cookieValue) throws ClientMessageException,
            ClientUnexpectedException;
    
    void changePassword(String currentPassword, String newPassword);
    
    void changeEmail(String newEmail);

}
