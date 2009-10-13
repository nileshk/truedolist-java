package com.myconnector.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

public abstract class GenericHibernateWithUserDAOImpl<T extends Serializable, KeyType extends Serializable>
        extends AbstractHibernateDAOImpl<T, KeyType> {

    @SuppressWarnings("unchecked")
    public List<T> getListForUser(KeyType userId) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.add(Expression.eq("userData.id", userId));
        criteriaForGetListForUser(criteria);
        return criteria.list();
    }

    protected void criteriaForGetListForUser(Criteria criteria) {
    }

}
