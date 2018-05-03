package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.UserSettingCustomRepository;

@Service
public class UserSettingRepositoryImpl implements UserSettingCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<UserSettingsCategoryMapping> getUserSettings(Long userid) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		Criteria crit = em.unwrap(Session.class).createCriteria(UserSettingsCategoryMapping.class);
		crit.add(Restrictions.eq("userid", userid));

		return crit.list();

	}

}