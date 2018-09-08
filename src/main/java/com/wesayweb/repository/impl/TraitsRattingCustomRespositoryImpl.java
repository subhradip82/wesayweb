package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wesayweb.model.TraitRattings;
import com.wesayweb.repository.TraitsRattingCustomRespository;

public class TraitsRattingCustomRespositoryImpl implements TraitsRattingCustomRespository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TraitRattings> getTraitRating(Long userTraitId, Long userId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(TraitRattings.class);
		crit.add(Restrictions.eq("userId", userId));
		crit.add(Restrictions.eq("userTraitId", userTraitId));
		return crit.list();
	}

	@Override
	public List<TraitRattings> getTraitRating(Long userTraitId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(TraitRattings.class);

		crit.add(Restrictions.eq("userTraitId", userTraitId));

		return crit.list();
	}

}
