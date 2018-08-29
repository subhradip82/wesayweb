package com.wesayweb.repository;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.CommentLikeDislike;

@Repository
public interface CommentLikeDislikeRespositoryCustom {

	public CommentLikeDislike giveLikeDislike(Long likeDislikeBy, Long commentId, int likeStatus);
}