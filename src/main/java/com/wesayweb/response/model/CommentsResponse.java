package com.wesayweb.response.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String commentsText;

	@Getter
	@Setter
	private String commentedBy;

	@Getter
	@Setter
	private String commentedDate;

	@Getter
	@Setter
	private int likeCount;

	@Getter
	@Setter
	private int disLikeCount;

	@Getter
	@Setter
	private Long commentId;
 
	@Getter
	@Setter
	@Builder.Default
	private int myLikeStatus = 0;
	
}