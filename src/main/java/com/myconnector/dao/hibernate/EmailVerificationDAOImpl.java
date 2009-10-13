package com.myconnector.dao.hibernate;

import com.myconnector.dao.EmailVerificationDAO;
import com.myconnector.domain.EmailVerification;

public class EmailVerificationDAOImpl extends
		GenericHibernateWithUserDAOImpl<EmailVerification, String> implements EmailVerificationDAO {

	@Override
	protected Class<EmailVerification> getDomainClass() {
		return EmailVerification.class;
	}

}
