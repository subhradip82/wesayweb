package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user_trait")
@EntityListeners(AuditingEntityListener.class)
public class UserTrait implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long targetuserid;
	private long traitid;
	private Date creationdate = new Date();
	private Date updationdate = new Date();
	private int isactive;
	private long givenbyuserid;
	private int isannonymous;
	private int typeofvote; // 0 = Positive , 1= Negetive , 2 = Nutral

	public int getTypeofvote() {
		return typeofvote;
	}

	public void setTypeofvote(int typeofvote) {
		this.typeofvote = typeofvote;
	}

	public Long getId() {
		return id;
	}

	public long getTargetuserid() {
		return targetuserid;
	}

	public void setTargetuserid(long targetuserid) {
		this.targetuserid = targetuserid;
	}

	public long getTraitid() {
		return traitid;
	}

	public void setTraitid(long traitid) {
		this.traitid = traitid;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getUpdationdate() {
		return updationdate;
	}

	public void setUpdationdate(Date updationdate) {
		this.updationdate = updationdate;
	}

	public int getIsactive() {
		return isactive;
	}

	public long getGivenbyuserid() {
		return givenbyuserid;
	}

	public void setGivenbyuserid(long givenbyuserid) {
		this.givenbyuserid = givenbyuserid;
	}

	public int getIsannonymous() {
		return isannonymous;
	}

	public void setIsannonymous(int isannonymous) {
		this.isannonymous = isannonymous;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserTrait() {
		super();
	}

}