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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.model.UserOtp;
import com.wesayweb.repository.TraitCustomRepository;
import com.wesayweb.repository.UserOtpCustomRepository;

@Service
public class UserOtpCustomRepositoryImpl implements UserOtpCustomRepository {

	@PersistenceContext
	private EntityManager em;
 	@Override
	@Transactional
	public boolean saveUserOtp(UserOtp userOtpObject) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserOtp> updateCriteria = cb.createCriteriaUpdate(UserOtp.class);
		Root<UserOtp> traitObj = updateCriteria.from(UserOtp.class);
		updateCriteria.set(traitObj.get("deletestatus"), 1);
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;

	}
	@Override
	public List<UserOtp> validateOtp(String otp, Long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(UserOtp.class);
		crit.add(Restrictions.eq("otp", otp.toLowerCase().trim()));
		crit.add(Restrictions.eq("userid", userid));
		return crit.list();
	}
	
	@Override
	@Transactional
	public boolean updateOtpStatus(Long userId, String otp) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserOtp> updateCriteria = cb.createCriteriaUpdate(UserOtp.class);
		Root<UserOtp> userObj = updateCriteria.from(UserOtp.class);
		updateCriteria.set(userObj.get("userid"), userId);
		updateCriteria.set(userObj.get("otp"), otp);
		updateCriteria.where(cb.equal(userObj.get("otpuseddate"), new Date()));
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}

}