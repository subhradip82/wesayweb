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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@Table(name = "user_badge")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserBadges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private Long badgegivenby;

	@Getter
	@Setter
	private Long badgegivenfor;

	@Getter
	@Setter
	private String responsetext;

	@Getter
	@Setter
	@Column(name = "abusestatus", nullable = false, columnDefinition = "int default 0")
	private int abusestatus;

	@Getter
	@Setter
	@Column(name = "deletestatus", nullable = false, columnDefinition = "int default 0")
	private int deletestatus;

	@Getter
	@Setter
	@Column(name = "hidenstatus", nullable = false, columnDefinition = "int default 0")
	private int hidenstatus;

	@Getter
	@Setter
	@Column(name = "readstatus", nullable = false, columnDefinition = "int default 0")
	private int readstatus;

	@Getter
	final Date givendate = new Date();

}