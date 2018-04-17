package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.repository.TraitCustomRepository;
@Service
public class TraitCustomRepositoryImpl implements TraitCustomRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Traits> getActiveTraits() {
		Criteria crit = em.unwrap(Session.class).createCriteria(Traits.class);
		crit.add(Restrictions.eq("is_active", 1));
		return crit.list();
	}

}
