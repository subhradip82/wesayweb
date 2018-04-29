package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesayweb.helper.WesayStringUtil;
import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;
import com.wesayweb.repository.TraitCustomRepository;

@Service
public class TraitCustomRepositoryImpl implements TraitCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Traits> getActiveTraits(int traitType, int limit) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("activestatus", 1));
		crit.add(Restrictions.eq("deletestatus", 0));
		if (traitType == 1) {
			crit.add(Restrictions.eq("traittype", "negative"));
		} else if (traitType == 2) {
			crit.add(Restrictions.eq("traittype", "positive"));
		} else if (traitType == 3) {
			crit.add(Restrictions.eq("traittype", "neutral"));
		}
		if (limit > 0) {
			crit.setMaxResults(limit);
		}
		crit.addOrder(Order.asc("traitname"));
		return crit.list();
	}

	@Override
	@Transactional
	public boolean removeTrait(Traits traitObj) {
		boolean returnValue = true;
		em.remove(traitObj);
		return returnValue;
	}

	@Override
	@Transactional
	public CustomTraits saveCustomTrait(CustomTraits customTrait) {
		String uniqueIdentifier = WesayStringUtil.generateRandomNumber();
		customTrait.setTraituniqueid(uniqueIdentifier);
		customTrait.setActivestatus(1);
		em.persist(customTrait);
		return customTrait;
	}

	@Override
	@Transactional
	public List<String> traitAlreadyExists(String traitname, long traitgivenby, long traitgivenfor) {

		return em.createNamedQuery("traitAlreadyExists").getResultList();

	}

}