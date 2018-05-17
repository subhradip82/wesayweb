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

import com.wesayweb.model.User;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.UserTraitCustomRepository;
import com.wesayweb.response.model.TraitListResponse;
import com.wesayweb.response.model.UserTraitsResponsePojo;

@Service
public class UserTraitRepositoryImpl implements UserTraitCustomRepository {

	@PersistenceContext
	private EntityManager em;

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
		updateCriteria.set(userObj.get("iswaitingforapproval"), userTraitObj.getIswaitingforapproval());
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();

	}

	@Override
	@Transactional
	public void approveCustomTrait(UserTrait userTraitObj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserTrait> updateCriteria = cb.createCriteriaUpdate(UserTrait.class);
		Root<UserTrait> userObj = updateCriteria.from(UserTrait.class);
		updateCriteria.set(userObj.get("iswaitingforapproval"), 0);
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();

	}
	
	@Override
	@Transactional
	public void hideTrait(UserTrait userTraitObj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserTrait> updateCriteria = cb.createCriteriaUpdate(UserTrait.class);
		Root<UserTrait> userObj = updateCriteria.from(UserTrait.class);
		updateCriteria.set(userObj.get("ishidden"),1);
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();

	}
	@Override
	@Transactional
	public void unHideTrait(UserTrait userTraitObj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserTrait> updateCriteria = cb.createCriteriaUpdate(UserTrait.class);
		Root<UserTrait> userObj = updateCriteria.from(UserTrait.class);
		updateCriteria.set(userObj.get("ishidden"),0);
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();
	}
	
	@Override
	@Transactional
	public void deleteTrait(UserTrait userTraitObj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserTrait> updateCriteria = cb.createCriteriaUpdate(UserTrait.class);
		Root<UserTrait> userObj = updateCriteria.from(UserTrait.class);
		updateCriteria.set(userObj.get("isactive"), 0);
		updateCriteria.set(userObj.get("updationdate"), new Date());
		updateCriteria.where(cb.equal(userObj.get("traitgivenfor"), userTraitObj.getTraitgivenfor()));
		updateCriteria.where(cb.equal(userObj.get("traituniqueid"), userTraitObj.getTraituniqueid()));
		em.createQuery(updateCriteria).executeUpdate();
		
	}

	@Override
	public List<Object[]> getMyFriendsTraits(long traitgivenfor, long traitgivenby) {
		return em.createNamedQuery("getMyFriendsTraits").getResultList();
	}

	@Override
	public List<UserTrait> listOfUnreadTrait(Long userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> traitsWaitingForApproval(Long userId) {
		return em.createNamedQuery("traitsWaitingForApproval").getResultList();
	}

	 
	 
}