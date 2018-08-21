package com.wesayweb.request.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CommentOnTrait {

	@Getter
	@Setter
	private Long commentId;

	
	
	@Getter
	@Setter
	private Long traitId;

	@Getter
	@Setter
	private Long parentCommentId;
	
	@Getter
	@Setter
	private String comment;
	
	
	
}
