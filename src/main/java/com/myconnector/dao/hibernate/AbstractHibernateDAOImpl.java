package com.myconnector.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDAOImpl<T extends Serializable, KeyType extends Serializable>
        extends HibernateDaoSupport {

    protected Class<T> domainClass = getDomainClass();

    /**
     * Method to return the class of the domain object
     */
    protected abstract Class<T> getDomainClass();

    @SuppressWarnings("unchecked")
    public T load(KeyType id) {
        return (T) getHibernateTemplate().load(domainClass, id);
    }

    public void update(T t) {
        getHibernateTemplate().update(t);
    }

    @SuppressWarnings("unchecked")
    public KeyType save(T t) {
        return (KeyType) getHibernateTemplate().save(t);
    }

    public void delete(T t) {
        getHibernateTemplate().delete(t);
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() {
        return (getHibernateTemplate().find("from " + domainClass.getName() + " x"));
    }

    public void deleteById(KeyType id) {
        Object obj = load(id);
        getHibernateTemplate().delete(obj);
    }

    public void deleteAll() {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                String hqlDelete = "delete " + domainClass.getName();
                int deletedEntities = session.createQuery(hqlDelete).executeUpdate();
                return null;
            }

        });
    }

    public int count() {
        List list = getHibernateTemplate().find(
                "select count(*) from " + domainClass.getName() + " x");
        Integer count = (Integer) list.get(0);
        return count.intValue();
    }

}