package com.wesayweb.repository.impl;

import java.util.ArrayList;
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
import com.wesayweb.repository.UserCustomRepository;

@Service
public class UserRepositoryImpl implements UserCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> getUserByEmailAddess(String emailaddress, int activestatus) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("emailaddress", emailaddress.toLowerCase().trim()));
		if (activestatus == 1) {
			crit.add(Restrictions.eq("isactive", 1));
		}
		return crit.list();

	}

	@Override
	public List<User> getUserByMobileNumber(String countryCode, String mobileNumber, int activestatus) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("countrycode", countryCode.toLowerCase().trim()));
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.toLowerCase().trim()));
		if (activestatus == 1) {
			crit.add(Restrictions.eq("isactive", 1));
		}
		return crit.list();

	}

	@Override
	public List<User> getUserByMobileNumberForUserSync(String countryCode, String mobileNumber, Long sessionUserId) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("countrycode", countryCode.toLowerCase().trim()));
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.toLowerCase().trim()));
		crit.add(Restrictions.ne("id", sessionUserId));
		crit.add(Restrictions.eq("isactive", 1));
		
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

	@Override
	public User findByUsername(String emailaddress) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("emailaddress", emailaddress.toLowerCase().trim()));
		List<User> resultSet = crit.list();
		if (resultSet.size() > 0) {
			return resultSet.get(0);
		} else {
			return new User();
		}
	}

	@Override
	public User findActiveUser(Long userid) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("id", userid));
		crit.add(Restrictions.eq("isactive", 1));
		List<User> resultSet = crit.list();
		if (resultSet.size() > 0) {
			return resultSet.get(0);
		} else {
			return new User();
		}
	}

	@Override
	public List<User> getUserByMobileEmail(String countryCode, String mobileNumber) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
	    crit.add(Restrictions.eq("countrycode", countryCode.toLowerCase().trim()));
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.toLowerCase().trim()));
		//crit.add(Restrictions.eq("emailaddress", emailaddress.toLowerCase().trim()));
		return crit.list();
	}

	@Override
	public List<User> getMyConfirmedFriendList(long userid) {
		List<User> returnList = new ArrayList<User>();
		List<Object[]> result = em.createNamedQuery("getMyConfirmedFriendList").getResultList();
		for (Object[] resultObj : result) {
			User userObj = new User();
			userObj.setId(Long.valueOf(resultObj[0].toString()));
			userObj.setFullname(resultObj[5].toString());
			userObj.setEmailaddress(resultObj[4].toString());
			userObj.setMobilenumber(resultObj[9].toString());
			returnList.add(userObj);
		}

		return returnList;
	}

	@Override
	public User getUserById(Long id) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("id",id));
		List<User> userList = crit.list();
		if(!userList.isEmpty()) {
			return userList.get(0);
		}
		else
		{
			return new User();
		}
	}


	 
}