package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "trait_ratting_master")
public class TraitRattings implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;

	private int rating;

	private Long userTraitId;

	private String traitIdentifier;

	@Getter
	@Setter
	@Column(name = "userId")
	private Long userId;

	@Getter
	final Date lastUpdatedOn = new Date();

}