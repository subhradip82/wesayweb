package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CommentLikeDislike;

@Repository
public interface CommentLikeDislikeRespository extends JpaRepository<CommentLikeDislike, Long> {

	public  CommentLikeDislike findBylikeDislikeByAndComment(Long likeDislikeBy, Long comment);	
}