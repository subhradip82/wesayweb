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
@Table(name = "trait_master")
@EntityListeners(AuditingEntityListener.class)
public class Traits implements Serializable {

	private static final long serialVersionUID = 1L;

	public Traits() {
		super();
	}

	public Traits(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String traitname;
	private String traitdescripion;
	private String traiticonpath;
	private int activestatus;
	private Date creationdate = new Date();
	private Date updateondate = new Date();
	private Date approveddate;

	@Column(name = "traitcreatedby", nullable = false, columnDefinition = "int default 0")
	private Long traitcreatedby;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTraitname() {
		return traitname;
	}

	public void setTraitname(String traitname) {
		this.traitname = traitname;
	}

	public int getActivestatus() {
		return activestatus;
	}

	public void setActivestatus(int activestatus) {
		this.activestatus = activestatus;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getUpdateondate() {
		return updateondate;
	}

	public void setUpdateondate(Date updateondate) {
		this.updateondate = updateondate;
	}

	public Long getTraitcreatedby() {
		return traitcreatedby;
	}

	public void setTraitcreatedby(Long traitcreatedby) {
		this.traitcreatedby = traitcreatedby;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getApproveddate() {
		return approveddate;
	}

	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}

	public String getTraitdescripion() {
		return traitdescripion;
	}

	public void setTraitdescripion(String traitdescripion) {
		this.traitdescripion = traitdescripion;
	}

	public String getTraiticonpath() {
		return traiticonpath;
	}

	public void setTraiticonpath(String traiticonpath) {
		this.traiticonpath = traiticonpath;
	}

	@Override
	public String toString() {
		return "Traits [id=" + id + ", traitname=" + traitname + ", traitdescripion=" + traitdescripion
				+ ", traiticonpath=" + traiticonpath + ", activestatus=" + activestatus + ", creationdate="
				+ creationdate + ", updateondate=" + updateondate + ", approveddate=" + approveddate
				+ ", traitcreatedby=" + traitcreatedby + "]";
	}

}
