package com.myconnector.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.myconnector.dao.InviteDAO;
import com.myconnector.domain.Invite;

public class InviteDAOImpl extends AbstractHibernateDAOImpl<Invite, String>
		implements InviteDAO {

	@Override
	protected Class<Invite> getDomainClass() {
		return Invite.class;
	}

	public Invite getInviteByToken(String token) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.add(Expression.eq("token", token));
        return (Invite) criteria.uniqueResult();		
	}

}
