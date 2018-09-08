package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wesayweb.model.TraitRattings;
import com.wesayweb.model.TraitsAggreeDisAggree;
import com.wesayweb.repository.VoteOnTraitRespositoryCustom;

public class VoteOnTraitRespositoryCustomImpl implements VoteOnTraitRespositoryCustom {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<TraitsAggreeDisAggree> getVoteList(String traitIdentifier, Long userTraitId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(TraitsAggreeDisAggree.class);
		crit.add(Restrictions.eq("traitUniqueIdentifier", traitIdentifier));
		crit.add(Restrictions.eq("userTraitId", userTraitId));
		return crit.list();
	}

	@Override
	public List<TraitsAggreeDisAggree> getMyVoteList(String traitIdentifier, Long userTraitId, Long userId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(TraitsAggreeDisAggree.class);
		crit.add(Restrictions.eq("traitUniqueIdentifier", traitIdentifier));
		crit.add(Restrictions.eq("userTraitId", userTraitId));
		crit.add(Restrictions.eq("userId", userId));
		
		return crit.list();
	}

}
