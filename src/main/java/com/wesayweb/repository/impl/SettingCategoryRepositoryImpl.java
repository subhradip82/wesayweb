package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.UserOtp;
import com.wesayweb.repository.SettingsCustomRepository;

@Service
public class SettingCategoryRepositoryImpl implements SettingsCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<SettingsCategory> getListOfSettingsCategory() {
		Criteria crit = em.unwrap(Session.class).createCriteria(SettingsCategory.class);
		return crit.list();
	}

}