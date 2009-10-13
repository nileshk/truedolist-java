package com.myconnector.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myconnector.dao.UserDataDAO;
import com.myconnector.domain.UserData;

import java.util.Iterator;
import java.util.List;

public class UserDataDAOImpl extends HibernateDaoSupport implements UserDataDAO {

    static Logger logger = Logger.getLogger(UserDataDAOImpl.class);

    public UserDataDAOImpl() {
    }

    public UserData load(Long id) {
        return (UserData) getHibernateTemplate().load(UserData.class, id);
    }

    public void update(UserData userData) {
        getHibernateTemplate().update(userData);
    }

    public void save(UserData userData) {
        getHibernateTemplate().save(userData);
    }

    public void delete(UserData userData) {
        getHibernateTemplate().delete(userData);
    }

    public List getList() {
        return (getHibernateTemplate()
                .find("from com.myconnector.domain.UserData userData"));
    }


    /*
     * @see com.myconnector.dao.UserDataDAO#getUserByUserName(java.lang.String)
     */
    public UserData getUserByUserLogin(String userLogin) {
        Iterator it = getHibernateTemplate()
                .iterate(
                        "from com.myconnector.domain.UserData user where user.userLogin = ?",
                        userLogin);
        if (it.hasNext()) {
            return (UserData) it.next();
        } else {
            return (null);
        }
    }

    public void deleteById(Long id) {
    	Object obj = load(id);
        getHibernateTemplate().delete(obj);
    }
}