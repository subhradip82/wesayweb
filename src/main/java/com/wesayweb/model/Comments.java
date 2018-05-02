package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity

@Table(name = "comments_master")
@EntityListeners(AuditingEntityListener.class)
public class Comments implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentid;
	private Long parrentcommentid;
	private Long commentedby;
	private Date creationdate = new Date();
	private String relatedtestimonialid;
	
	@Column(name = "commentactivestatus", nullable = false, columnDefinition = "int default 0")
	private int commentactivestatus;
	
	
	
}
