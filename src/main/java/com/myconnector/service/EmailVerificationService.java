package com.myconnector.service;

import com.myconnector.domain.UserData;

public interface EmailVerificationService {

	UserData verifyEmail(String token);

}
