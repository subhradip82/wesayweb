package com.wesayweb.model;

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
@Table(name = "app_version")
@EntityListeners(AuditingEntityListener.class)
public class AppVersionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date creationdate = new Date();

	private String version;

	@Column(name = "isrecent", nullable = false, columnDefinition = "int default 100")
	private int isrecent;

	@Column(name = "isminor", nullable = false, columnDefinition = "int default 100")
	private int isminor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getIsrecent() {
		return isrecent;
	}

	public void setIsrecent(int isrecent) {
		this.isrecent = isrecent;
	}

	public int getIsminor() {
		return isminor;
	}

	public void setIsminor(int isminor) {
		this.isminor = isminor;
	}

}
