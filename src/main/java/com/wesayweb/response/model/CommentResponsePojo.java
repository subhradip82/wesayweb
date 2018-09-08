package com.wesayweb.response.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class CommentResponsePojo implements Serializable { 
	 
	private static final long serialVersionUID = 1L;
	
	private String commentText;
	private String commentBy;
	@JsonFormat(pattern = "dd/MMM/yyyy HH:mm")
	private Date commentDate;
	private Long commentId;
	private int likeCount;
	private int disLikeCount;
	private int myLikeDislikeStatus;
	private List<CommentResponseUserPojo> likeDislike;
}
