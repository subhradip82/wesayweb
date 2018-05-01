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
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "custom_traits")
@EntityListeners(AuditingEntityListener.class)
public class CustomTraits implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String traitname;
	private String traittype;
	private String traitdescripion;
	private String traiticonpath;
	private int activestatus;
	@Column(name = "deletestatus", nullable = false, columnDefinition = "int default 0")
	private int deletestatus;
	private Date creationdate = new Date();
	private Date updateondate = new Date();
	private Date approveddate;
	@Column(name = "traitidentifier", nullable = false, columnDefinition = "int default 100")
	private int traitidentifier;
	
	private String traituniqueid;
	
	@Transient
	private Long traitgivenfor;
	
	
	public Long getTraitgivenfor() {
		return traitgivenfor;
	}
	public void setTraitgivenfor(Long traitgivenfor) { 
		this.traitgivenfor = traitgivenfor;
	}
	public String getTraituniqueid() {
		return traituniqueid;
	}
	public void setTraituniqueid(String traituniqueid) {
		this.traituniqueid = traituniqueid;
	}
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
	public String getTraittype() {
		return traittype;
	}
	public void setTraittype(String traittype) {
		this.traittype = traittype;
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
	public int getActivestatus() {
		return activestatus;
	}
	public void setActivestatus(int activestatus) {
		this.activestatus = activestatus;
	}
	public int getDeletestatus() {
		return deletestatus;
	}
	public void setDeletestatus(int deletestatus) {
		this.deletestatus = deletestatus;
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
	public Date getApproveddate() {
		return approveddate;
	}
	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}
	public int getTraitidentifier() {
		return traitidentifier;
	}
	public void setTraitidentifier(int traitidentifier) {
		this.traitidentifier = traitidentifier;
	}
	@Override
	public String toString() {
		return "CustomTraits [id=" + id + ", traitname=" + traitname + ", traittype=" + traittype + ", traitdescripion="
				+ traitdescripion + ", traiticonpath=" + traiticonpath + ", activestatus=" + activestatus
				+ ", deletestatus=" + deletestatus + ", creationdate=" + creationdate + ", updateondate=" + updateondate
				+ ", approveddate=" + approveddate + ", traitidentifier=" + traitidentifier + ", traituniqueid="
				+ traituniqueid + "]";
	}

}