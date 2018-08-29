package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>, CommentRepositoryCustom {

}