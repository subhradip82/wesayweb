package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesayweb.model.Traits;
import com.wesayweb.repository.TraitCustomRepository;

@Service
public class TraitCustomRepositoryImpl implements TraitCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Traits> getActiveTraits(int traitType) {
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("activestatus", 1));
		crit.add(Restrictions.eq("deletestatus", 0));
		if(traitType==1)
		{
			crit.add(Restrictions.eq("traittype", "negative"));
		}
		else if(traitType==2)
		{
			crit.add(Restrictions.eq("traittype", "positive"));
		}
		else if(traitType==3)
		{
			crit.add(Restrictions.eq("traittype", "neutral"));
		}
		
		crit.addOrder(Order.asc("traitname"));
		return crit.list();
	}

	@Override
	public boolean traitAlreadyExists(String traitName) {
		boolean returnValue = false;
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("traitname", traitName.trim()).ignoreCase());
		if (crit.list().size() > 0) {
			returnValue = false;
		}
		return returnValue;
	}

	@Override
	public boolean ifTraitIsUpdatable(Traits trailObj) {
		boolean returnValue = false;
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("traitname", trailObj.getTraitname()).ignoreCase());
		crit.add(Restrictions.ne("id", trailObj.getId()));
		crit.add(Restrictions.eq("traitcreatedby", trailObj.getTraitcreatedby()));
		if (crit.list().size() > 0) {
			returnValue = false;
		}
		return returnValue;
	}

	@Override
	@Transactional
	public boolean updateTrait(Traits traitObj) {
		boolean returnValue = true;
		em.merge(traitObj);
		return returnValue;
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
	public boolean deleteTrait(Long traitid) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Traits> updateCriteria = cb.createCriteriaUpdate(Traits.class);
		Root<Traits> traitObj = updateCriteria.from(Traits.class);
		updateCriteria.set(traitObj.get("deletestatus"), 1);
		updateCriteria.where(cb.equal(traitObj.get("id"), traitid));
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}

}