package com.wesayweb.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.UserTraitCustomRepository;

@Service
public class UserTraitRepositoryImpl implements UserTraitCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<UserTrait> getMyTraits(long giveuserid, long targetuserid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(UserTrait.class);
		crit.add(Restrictions.eq("givenbyuserid", giveuserid));
		crit.add(Restrictions.eq("targetuserid", targetuserid));
		// crit.add(Restrictions.eq("isactive", 1));
		return crit.list();
	}

	@Override
	public List<Object[]> getMyTraits(long targetuserid) {
		return em.createNamedQuery("getmytraits").getResultList();

	}

	@Override
	@Transactional
	public void saveUserTraits(UserTrait userTraitObj) {

		em.merge(userTraitObj);

	}

	@Override
	@Transactional
	public void updateUserTrait(UserTrait userTraitObj) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserTrait> updateCriteria = cb.createCriteriaUpdate(UserTrait.class);
		Root<UserTrait> userObj = updateCriteria.from(UserTrait.class);
		updateCriteria.set(userObj.get("ishidden"), userTraitObj.getIshidden());
		updateCriteria.set(userObj.get("isactive"), userTraitObj.getIsactive());
		updateCriteria.set(userObj.get("isannonymous"), userTraitObj.getIsannonymous());
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();

	}

}