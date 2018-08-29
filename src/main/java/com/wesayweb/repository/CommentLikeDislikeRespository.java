package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CommentLikeDislike;

@Repository
public interface CommentLikeDislikeRespository extends JpaRepository<CommentLikeDislike, Long>, CommentLikeDislikeRespositoryCustom {
}