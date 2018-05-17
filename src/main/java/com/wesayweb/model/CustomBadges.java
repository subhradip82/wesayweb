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

import com.wesayweb.helper.WesayStringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@Table(name = "custom_badge")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomBadges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long badgeid;

	@Getter
	@Setter
	private Long badgecreatedby;
	
	@Getter
	@Setter
	private String badgename;

	@Getter
	@Setter
	private String badgedescription;

	@Getter
	@Setter
	private String badgeicon;
 
	@Getter
	@Setter
	@Column(name = "badgeisactive", nullable = false, columnDefinition = "int default 1")
	private int badgeisactive;
	
	@Getter
	final Date addeddate = new Date();

	@Getter
	final String badgeuiqueid = WesayStringUtil.generateRandomNumber();	
	
}