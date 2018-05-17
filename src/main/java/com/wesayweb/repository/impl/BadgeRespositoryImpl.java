package com.wesayweb.repository.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wesayweb.model.Badges;
import com.wesayweb.model.Friends;
import com.wesayweb.repository.BadgeCustomRespository;

public class BadgeRespositoryImpl implements BadgeCustomRespository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BigInteger> getNumberOfEligibleBadges(long sourceuserid) {
		return entityManager.createNamedQuery("getNumberOfEligibleBadges").getResultList();

	}

	@Override
	public List<Badges> getAvailableBadges() {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Badges.class);
		crit.add(Restrictions.eq("badgeisactive", 1));
		crit.addOrder(Order.asc("badgename"));
		return crit.list();
	}

}
