package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.FirstImpression;
import com.wesayweb.model.Traits;
import com.wesayweb.repository.FirstImpressionCustomRepository;

@Service
public class FirstImpressionCustomRepositoryImpl implements FirstImpressionCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<FirstImpression> getUserFirstImpressions(Long givenby, Long givenfor) {
		Criteria crit = em.unwrap(Session.class).createCriteria(FirstImpression.class);
		crit.add(Restrictions.eq("impressiongivenby", givenby));
		crit.add(Restrictions.eq("impressiongivenfor", givenfor));
		crit.addOrder(Order.asc("creationdate"));
		return crit.list();

	}

 
}