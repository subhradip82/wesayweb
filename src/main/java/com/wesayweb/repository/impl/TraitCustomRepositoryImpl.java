package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.Traits;
import com.wesayweb.repository.TraitCustomRepository;

@Service
public class TraitCustomRepositoryImpl implements TraitCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Traits> getActiveTraits() {
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("activestatus", 1));
		crit.addOrder(Order.asc("traitname"));
		return crit.list();
	}

	@Override
	public boolean traitAlreadyExists(String traitName) {
		boolean returnValue = false;
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("traitname", traitName).ignoreCase());
		if (crit.list().size() > 0) {
			returnValue = true;
		}

		return returnValue;
	}

	@Override
	public boolean ifTraitIsUpdatable(Traits trailObj) {
		boolean returnValue = false;
		System.out.println(trailObj.toString());
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("traitname", trailObj.getTraitname()).ignoreCase());
		crit.add(Restrictions.ne("id", trailObj.getId()));
		crit.add(Restrictions.eq("traitcreatedby", trailObj.getTraitcreatedby()));
		if (crit.list().size() > 0) {
			returnValue = false;
		}
		return returnValue;
	}

}