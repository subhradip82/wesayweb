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

	@Override

	public List<Object[]> getMySettings(long userid) {
		return em.createNamedQuery("getmysettings").getResultList();

	}

	@Override
	@Transactional
	public boolean changeMySetting(UserSettingsCategoryMapping settingObj) {
		boolean returnValue = false;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<UserSettingsCategoryMapping> updateCriteria = cb
				.createCriteriaUpdate(UserSettingsCategoryMapping.class);
		Root<UserSettingsCategoryMapping> userObj = updateCriteria.from(UserSettingsCategoryMapping.class);
		updateCriteria.set(userObj.get("categoryvalue"), settingObj.getCategoryvalue());
		updateCriteria.where(cb.equal(userObj.get("userid"), settingObj.getUserid()));
		updateCriteria.where(cb.equal(userObj.get("uniqueid"), settingObj.getUniqueid()));
		int affected = em.createQuery(updateCriteria).executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}
}