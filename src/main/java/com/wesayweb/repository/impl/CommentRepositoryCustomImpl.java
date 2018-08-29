package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wesayweb.model.Comments;
import com.wesayweb.model.Friends;
import com.wesayweb.repository.CommentRepositoryCustom;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public List<Comments> getCommentList(Long traitId) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Comments.class);
		crit.add(Restrictions.eq("userTraitId", traitId));
		return crit.list();
	}

}
