package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity

@Table(name = "comments_master")

public class Comments implements Serializable {

	public Comments(Long parentCommentId) {
		this.commentid = parentCommentId;
	}

	public Comments() {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long commentid;

	@Getter
	@Setter
	private Long userTraitId;

	@Getter
	@Setter
	private String traitIdentifier;
	
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User commentedby;

	@Getter
	@Setter
	@Column(name = "commenttext", length = 10000)
	private String commentText;

	@Getter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
	final Date creationdate = new Date();

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userTraitId", referencedColumnName = "id", insertable = false, updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private UserTrait traitId;

	@Getter
	@Setter
	@Column(name = "commentactivestatus", nullable = false, columnDefinition = "int default 0")
	private int commentactivestatus;

	@Getter
	@Setter
	@Column(name = "deletestatus", nullable = false, columnDefinition = "int default 0")
	private int deletestatus;

	
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "commentId", referencedColumnName = "commentId", insertable = false, updatable = false)
	private List<CommentLikeDislike> likeDislike; 
}