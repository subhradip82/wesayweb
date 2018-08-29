package com.wesayweb.request.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentOnTrait implements Serializable {

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
	
	
	@Getter
	@Setter
	private int like;
	
	
}
