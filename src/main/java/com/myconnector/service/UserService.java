package com.myconnector.service;

import com.myconnector.domain.UserData;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public interface UserService extends OldGenericService {

    void deleteUser(UserData userData);            

    UserData getUserByUserLogin(String userLogin);

    UserData getUserById(Long id);
    
    void saveUser(UserData userData);

    void updateUser(UserData userData);

	void changeEmail(String newEmail);
	
}