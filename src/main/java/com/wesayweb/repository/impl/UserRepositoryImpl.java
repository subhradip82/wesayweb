package com.wesayweb.repository.impl;

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

import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.repository.UserCustomRepository;

@Service
public class UserRepositoryImpl implements UserCustomRepository {

	 
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> getUserByEmailAddess(String emailaddress, int activestatus) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("emailaddress", emailaddress.toLowerCase().trim()));
		if(activestatus==1) {
		crit.add(Restrictions.eq("isactive", 1));
		}
		return crit.list();

	}

	@Override
	public List<User> getUserByMobileNumber(String countryCode, String mobileNumber, int activestatus) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("countrycode", countryCode.toLowerCase().trim()));
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.toLowerCase().trim()));
		if(activestatus==1) {
			crit.add(Restrictions.eq("isactive", 1));
			}
		return crit.list();

	}

	@Override
	@Transactional
	public boolean activateUser(Long userId) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<User> updateCriteria = cb.createCriteriaUpdate(User.class);
		Root<User> userObj = updateCriteria.from(User.class);
		updateCriteria.set(userObj.get("isactive"), 1);
		updateCriteria.where(cb.equal(userObj.get("id"), userId));
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}

	@Override
	@Transactional 
	public boolean changeUserPassword(Long userId, String password) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<User> updateCriteria = cb.createCriteriaUpdate(User.class);
		Root<User> userObj = updateCriteria.from(User.class);
		updateCriteria.set(userObj.get("password"), password);
		updateCriteria.where(cb.equal(userObj.get("id"), userId));
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}

}
