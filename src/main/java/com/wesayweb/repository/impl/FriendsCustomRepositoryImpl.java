package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.Friends;
import com.wesayweb.repository.FriendsCustomRepository;
import com.wesayweb.response.model.FriendsResponse;

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

	@Override
	public List<Friends> getMyFriendRequest(Long userid, Long requestid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		crit.add(Restrictions.eq("friendsid", userid));
		crit.add(Restrictions.eq("id", requestid));
		crit.add(Restrictions.eq("invitationacceptstatus", 0));
		crit.add(Restrictions.isNotNull("requestuniueid"));
		crit.add(Restrictions.isNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));
		return crit.list();
	}

	@Override
	public boolean areTheyFriends(Long firstFriendId, Long secondFriendId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		
		Criterion rest1= Restrictions.or(Restrictions.eq("userid", firstFriendId),
				Restrictions.eq("friendsid", firstFriendId));
		
		Criterion rest2= Restrictions.or(Restrictions.eq("userid", firstFriendId),
				Restrictions.eq("friendsid", firstFriendId));
		
		
		crit.add(Restrictions.and(rest1, rest2));
		 
		crit.add(Restrictions.eq("invitationacceptstatus", 1));
		crit.add(Restrictions.isNotNull("requestuniueid"));
		crit.add(Restrictions.isNotNull("invitationacceptdate"));

		if (crit.list().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Object[]> getMyFriendList(long userid) {
		return em.createNamedQuery("getMyFriendList").getResultList();
	}

}