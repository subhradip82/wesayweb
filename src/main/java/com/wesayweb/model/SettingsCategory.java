package com.wesayweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity

@Table(name = "settings_category")
@EntityListeners(AuditingEntityListener.class)

public class SettingsCategory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String categoryname;
	private String categorydescription;

	@Column(name = "allowedmultiplevalue", nullable = false, columnDefinition = "int default 0")
	private int allowedmultiplevalue;
	private int defaultvalue;
	private String uniqueid;

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public int getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(int defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCategorydescription() {
		return categorydescription;
	}

	public void setCategorydescription(String categorydescription) {
		this.categorydescription = categorydescription;
	}

	public int getAllowedmultiplevalue() {
		return allowedmultiplevalue;
	}

	public void setAllowedmultiplevalue(int allowedmultiplevalue) {
		this.allowedmultiplevalue = allowedmultiplevalue;
	}

}