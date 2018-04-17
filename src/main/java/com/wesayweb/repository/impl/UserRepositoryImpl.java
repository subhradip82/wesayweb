package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.User;
import com.wesayweb.repository.UserCustomRepository;

@Service
public class UserRepositoryImpl implements UserCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> getUserByEmailAddess(String emailaddress) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("emailaddress", emailaddress.toLowerCase().trim()));
		return crit.list();

	}
	
	@Override
	public List<User> getUserByMobileNumber(String countryCode, String mobileNumber) {
		Criteria crit = em.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("countrycode", countryCode.toLowerCase().trim()));
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.toLowerCase().trim()));
		return crit.list();

	}
	
}
