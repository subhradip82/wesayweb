package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity

@Table(name = "comments_like_dislike")
public class CommentLikeDislike implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private Long commentId;
	
	@Getter
	@Setter
	private Long userId;
	
	
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User likeDislikeBy;

	@Getter
	final Date lastUpdatedOn = new Date();

	 
	@Getter
	@Setter
	@Column(name = "likeStatus", nullable = false, columnDefinition = "int default 0")
	private int likeDislikeStatus;
}