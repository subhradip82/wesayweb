package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
 
import com.wesayweb.model.Friends;
import com.wesayweb.repository.FriendsCustomRepository;

@Service
public class FriendsCustomRepositoryImpl implements FriendsCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Friends> getMyFriendRequest(Long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		crit.add(Restrictions.eq("friendsid", userid));
		crit.add(Restrictions.eq("invitationacceptstatus", 0));
		crit.add(Restrictions.isNotNull("requestuniueid"));
		crit.add(Restrictions.isNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));
		return crit.list();
	}

}