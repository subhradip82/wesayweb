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
@Table(name = "user_trait")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserTrait implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserTrait(Long id)
	{
	this.id = id;	
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private long traitid;

	@Getter
	final Date creationdate = new Date();

	@Getter
	@Setter
	private Date updationdate;

	@Getter
	@Setter
	@Column(name = "isactive", nullable = false, columnDefinition = "int default 1")
	private int isactive;

	@Getter
	@Setter
	private long traitgivenby;

	@Getter
	@Setter
	private long traitgivenfor;

	@Getter
	@Setter
	@Column(name = "isannonymous", nullable = false, columnDefinition = "int default 0")
	private int isannonymous;

	@Getter
	@Setter
	@Column(name = "iswaitingforapproval", nullable = false, columnDefinition = "int default 0")
	private int iswaitingforapproval;

	@Getter
	@Setter
	private String traituniqueid;

	@Getter
	@Setter
	@Column(name = "readstatus", nullable = false, columnDefinition = "int default 0")
	private int readstatus;

	@Getter
	@Setter
	@Column(name = "ishidden", nullable = false, columnDefinition = "int default 0")
	private int ishidden;

	@Getter
	@Setter
	@Column(name = "isCountHidden", nullable = false, columnDefinition = "int default 0")
	private int isCountHidden;

	
	@Getter
	@Setter
	private int typeofvote; // 0 = Positive , 1= Negetive , 2 = Nutral

	@Getter
	@Setter
	@Column(name = "subtypeofvote", nullable = false, columnDefinition = "int default 0")
	private int subtypeofvote;

	@Getter
	@Setter
	private String comment;

	@Getter
	@Setter
	 
	@Column(name="totalRatings", columnDefinition="Decimal(10,2) default '0.0'")
	private Double totalRatings; 

	
}