package com.myconnector.dao.hibernate;

import com.myconnector.dao.UserCookieDAO;
import com.myconnector.domain.UserCookie;

public class UserCookieDAOImpl extends AbstractHibernateDAOImpl<UserCookie, String> implements
        UserCookieDAO {

    @Override
    protected Class<UserCookie> getDomainClass() {
        return UserCookie.class;
    }

}
