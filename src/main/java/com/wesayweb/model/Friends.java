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

@Table(name = "friend_list")
@EntityListeners(AuditingEntityListener.class)

public class Friends implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long  userid;
	private Long  friendsid;
	private int   addedvia;
	private Date  addeddate = new Date();
	private int   invitationacceptstatus;
	private Long   invitedby;
	private int   activestatus;
	private Date  invitationacceptdate;
	private String requestuniueid;
	
	public String getRequestuniueid() {
		return requestuniueid;
	}

	public void setRequestuniueid(String requestuniueid) {
		this.requestuniueid = requestuniueid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getFriendsid() {
		return friendsid;
	}

	public void setFriendsid(Long friendsid) {
		this.friendsid = friendsid;
	}

	public int getAddedvia() {
		return addedvia;
	}

	public void setAddedvia(int addedvia) {
		this.addedvia = addedvia;
	}

	public Date getAddeddate() {
		return addeddate;
	}

	public void setAddeddate(Date addeddate) {
		this.addeddate = addeddate;
	}

	public int getInvitationacceptstatus() {
		return invitationacceptstatus;
	}

	public void setInvitationacceptstatus(int invitationacceptstatus) {
		this.invitationacceptstatus = invitationacceptstatus;
	}

	public Long getInvitedby() {
		return invitedby;
	}

	public void setInvitedby(Long invitedby) {
		this.invitedby = invitedby;
	}

	public int getActivestatus() {
		return activestatus;
	}

	public void setActivestatus(int activestatus) {
		this.activestatus = activestatus;
	}

	public Date getInvitationacceptdate() {
		return invitationacceptdate;
	}

	public void setInvitationacceptdate(Date invitationacceptdate) {
		this.invitationacceptdate = invitationacceptdate;
	}

}