package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.Comments;

@Repository
public interface CommentRepositoryCustom {

	public List<Comments> getCommentList(String traitIdentifier);
	
}