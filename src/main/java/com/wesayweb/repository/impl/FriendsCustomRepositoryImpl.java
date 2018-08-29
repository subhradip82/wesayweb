package com.wesayweb.repository.impl;

import java.util.ArrayList;
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
import com.wesayweb.model.User;
import com.wesayweb.repository.FriendsCustomRepository;

@Service
public class FriendsCustomRepositoryImpl implements FriendsCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Friends> getMySentFriendRequest(Long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		crit.add(Restrictions.eq("invitedby", userid));
		crit.add(Restrictions.eq("invitationacceptstatus", 0));
		crit.add(Restrictions.isNotNull("requestuniqueid"));
		crit.add(Restrictions.isNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));
		return crit.list();
	}

	@Override
	public List<Friends> getMyRecievedFriendRequest(Long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		crit.add(Restrictions.eq("friendsid", userid));
		crit.add(Restrictions.eq("invitationacceptstatus", 0));
		crit.add(Restrictions.isNotNull("requestuniqueid"));
		crit.add(Restrictions.isNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));
		return crit.list();
	}

	@Override
	public List<Friends> getMyFriendRequest(Long userid, Long requestid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		crit.add(Restrictions.eq("friendsid", userid));
		crit.add(Restrictions.eq("invitedby", requestid));
		crit.add(Restrictions.eq("invitationacceptstatus", 0));
		crit.add(Restrictions.isNotNull("requestuniqueid"));
		crit.add(Restrictions.isNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));
		return crit.list();
	}

	@Override
	public boolean areTheyFriends(Long firstFriendId, Long secondFriendId) {

		System.err.println("firstFriendId:" + firstFriendId + "  secondFriendId:" + secondFriendId);
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);

		Criterion rest1 = Restrictions.and(Restrictions.eq("userid", firstFriendId),
				Restrictions.eq("friendsid", secondFriendId));

		Criterion rest2 = Restrictions.and(Restrictions.eq("userid", secondFriendId),
				Restrictions.eq("friendsid", firstFriendId));

		crit.add(Restrictions.or(rest1, rest2));
		crit.add(Restrictions.eq("invitationacceptstatus", 1));
		// crit.add(Restrictions.isNotNull("requestuniqueid"));
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

	@Override
	public List<Friends> getMyActiveFriends(long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Friends.class);
		Criterion c2 = Restrictions.eq("friendsid", userid);
		Criterion c3 = Restrictions.eq("invitedby", userid);
		crit.add(Restrictions.or(c2, c3));
		crit.add(Restrictions.eq("invitationacceptstatus", 1));
		crit.add(Restrictions.isNotNull("requestuniqueid"));
		crit.add(Restrictions.isNotNull("invitationacceptdate"));
		crit.addOrder(Order.asc("addeddate"));

		return crit.list();
	}

	
}