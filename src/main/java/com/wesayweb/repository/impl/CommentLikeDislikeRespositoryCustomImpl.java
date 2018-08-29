package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wesayweb.model.CommentLikeDislike;
import com.wesayweb.model.Friends;
import com.wesayweb.repository.CommentLikeDislikeRespositoryCustom;

public class CommentLikeDislikeRespositoryCustomImpl implements CommentLikeDislikeRespositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public CommentLikeDislike giveLikeDislike(Long likeDislikeBy, Long commentId, int likeStatus) {

		Criteria crit = entityManager.unwrap(Session.class).createCriteria(CommentLikeDislike.class);
		crit.add(Restrictions.eq("commentId", commentId));
		crit.add(Restrictions.eq("userId", likeDislikeBy)); 
		List<CommentLikeDislike> result =  crit.list();
		CommentLikeDislike obj = new CommentLikeDislike();
		if(result.size()>0) {
			obj = result.get(0);
			obj.setLikeDislikeStatus(likeStatus);
		}
		else
		{
			obj.setCommentId(commentId);
			obj.setUserId(likeDislikeBy);
			obj.setLikeDislikeStatus(likeStatus);
			
		}
		return obj;
	}

}
