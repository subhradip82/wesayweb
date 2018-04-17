package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Service;

import com.wesayweb.model.User;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.UserCustomRepository;
import com.wesayweb.repository.UserTraitCustomRepository;

@Service
public class UserTraitRepositoryImpl implements UserTraitCustomRepository {

	@PersistenceContext
	private EntityManager em;
  
	@Override
	public List<UserTrait> getMySelfTraits(long userid) {
		Query q = em.createNativeQuery("SELECT a.id, a.trait_name  FROM UserTrait a", UserTrait.class);
		return null;
	}
	
}
