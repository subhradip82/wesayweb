package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;

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
		Query q = em.createQuery("update UserSettingsCategoryMapping set categoryvalue=:categoryvalue "
				+ "  where uniqueid=:uniqueid and userid =:userid");
		q.setParameter("categoryvalue", settingObj.getCategoryvalue());
		q.setParameter("uniqueid", settingObj.getUniqueid());
		q.setParameter("userid", settingObj.getUserid());
		int affected = q.executeUpdate();
		if (affected > 0) {
			returnValue = true;
		}
		return returnValue;
	}
}