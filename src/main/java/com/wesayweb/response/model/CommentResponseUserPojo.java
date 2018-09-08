package com.wesayweb.response.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class CommentResponseUserPojo implements Serializable { 
	 
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	@JsonFormat(pattern = "dd/MMM/yyyy HH:mm")
	private Date likeDislikeDate;
	private int likeOrDislike;

}
