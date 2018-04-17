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
@Table(name = "trait_master")
@EntityListeners(AuditingEntityListener.class)
public class Traits implements Serializable {
	/**
	 * 
	 */
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
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String trait_name;
	private int is_active;
	private Date creationdate = new Date();
	private Date updateondate = new Date();

	public String getTrait_name() {
		return trait_name;
	}
	public void setTrait_name(String trait_name) {
		this.trait_name = trait_name;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
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

	

}
