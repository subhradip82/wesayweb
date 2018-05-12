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

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_contact_list")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ContactList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long contactid;

	@Getter
	@Setter
	private Long sourceuserid;

	@Getter
	@Setter
	private String emailaddress;

	@Getter
	@Setter
	private String countrycode;

	@Getter
	@Setter
	private String mobilenumber;

	@Getter
	@Setter
	private Date syncdate = new Date();

	@Getter
	@Setter
	private String firstname;

	@Getter
	@Setter
	private String lastname;

	@Getter
	@Setter
	private String fullname;

	@Getter
	@Setter
	@Column(name = "isregistredinwesay", nullable = false, columnDefinition = "int default  0")
	private int isregistredinwesay;

	@Getter
	@Setter
	@Column(name = "isinviationsent", nullable = false, columnDefinition = "int default  0")
	private int isinviationsent;

}
