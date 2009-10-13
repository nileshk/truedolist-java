package com.myconnector.domain;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.myconnector.util.TokenUtil;

public class CookieIdGenerator implements IdentifierGenerator {

	public CookieIdGenerator() {
	}

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		return TokenUtil.getToken();

	}

}
