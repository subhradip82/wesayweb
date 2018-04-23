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
@Table(name = "user_otp_master")
@EntityListeners(AuditingEntityListener.class)
public class UserOtp implements Serializable {

	@Override
	public String toString() {
		return "UserOtp [id=" + id + ", userid=" + userid + ", otp=" + otp + ", otpusedstatus=" + otpusedstatus
				+ ", creationdate=" + creationdate + ", validupto=" + validupto + ", otpuseddate=" + otpuseddate + "]";
	}

	private static final long serialVersionUID = 1L;

	public UserOtp() {
		super();
	}

	public UserOtp(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userid;
	private String otp;
	@Column(name = "otpusedstatus", nullable = false, columnDefinition = "int default 0")
	private int otpusedstatus;
	private Date creationdate = new Date();
	private Date validupto = addMinutesToDate(10, new Date());
	private Date otpuseddate;
 
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public int getOtpusedstatus() {
		return otpusedstatus;
	}

	public void setOtpusedstatus(int otpusedstatus) {
		this.otpusedstatus = otpusedstatus;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getValidupto() {
		return validupto;
	}

	public void setValidupto(Date validupto) {
		this.validupto = validupto;
	}

	public Date getOtpuseddate() {
		return otpuseddate;
	}

	public void setOtpuseddate(Date otpuseddate) {
		this.otpuseddate = otpuseddate;
	}

	public static Date addMinutesToDate(int minutes, Date beforeTime){
	    final long ONE_MINUTE_IN_MILLIS = 60000;
	    long curTimeInMs = beforeTime.getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
	    return afterAddingMins;
	}
}