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
@Table(name = "user_trait")
@EntityListeners(AuditingEntityListener.class)
public class UserTrait implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long traitid;
	private Date creationdate = new Date();
	private Date updationdate;

	@Column(name = "isactive", nullable = false, columnDefinition = "int default 1")
	private int isactive;
	private long traitgivenby;
	private long traitgivenfor;
	@Column(name = "isannonymous", nullable = false, columnDefinition = "int default 0")
	private int isannonymous;
	@Column(name = "iswaitingforapproval", nullable = false, columnDefinition = "int default 0")
    private int iswaitingforapproval;
	private String traituniqueid;
	
	@Column(name = "readstatus", nullable = false, columnDefinition = "int default 0")
	private int readstatus;
	
	public int getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(int readstatus) {
		this.readstatus = readstatus;
	}

	public int getIswaitingforapproval() {
		return iswaitingforapproval;
	}

	public void setIswaitingforapproval(int iswaitingforapproval) {
		this.iswaitingforapproval = iswaitingforapproval;
	}

	public String getTraituniqueid() {
		return traituniqueid;
	}

	public void setTraituniqueid(String traituniqueid) {
		this.traituniqueid = traituniqueid;
	}

	@Column(name = "ishidden", nullable = false, columnDefinition = "int default 0")
	private int ishidden;

	private int typeofvote; // 0 = Positive , 1= Negetive , 2 = Nutral 

	@Column(name = "subtypeofvote", nullable = false, columnDefinition = "int default 0")
	private int subtypeofvote ; 
	
	private String comment;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public long getTraitgivenby() {
		return traitgivenby;
	}

	public void setTraitgivenby(long traitgivenby) {
		this.traitgivenby = traitgivenby;
	}

	public Long getTraitgivenfor() {
		return traitgivenfor;
	}

	public void setTraitgivenfor(Long traitgivenfor) {
		this.traitgivenfor = traitgivenfor;
	}

	public int getIsannonymous() {
		return isannonymous;
	}

	public void setIsannonymous(int isannonymous) {
		this.isannonymous = isannonymous;
	}

	public int getIshidden() {
		return ishidden;
	}

	public void setIshidden(int ishidden) {
		this.ishidden = ishidden;
	}

	public int getTypeofvote() {
		return typeofvote;
	}

	public void setTypeofvote(int typeofvote) {
		this.typeofvote = typeofvote;
	}

	public int getSubtypeofvote() {
		return subtypeofvote;
	}

	public void setSubtypeofvote(int subtypeofvote) {
		this.subtypeofvote = subtypeofvote;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
}