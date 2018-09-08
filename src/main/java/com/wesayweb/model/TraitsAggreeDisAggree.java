package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "traits_aggree_disaggree")
@AllArgsConstructor
@NoArgsConstructor
public class TraitsAggreeDisAggree implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private Long userTraitId;

	@Getter
	@Setter
	private String traitUniqueIdentifier;

	@Getter
	@Setter
	@Column(name = "userId")
	private Long userId;
 
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
	private User aggreeDisAggreeBy;

	@Getter
	final Date lastUpdatedOn = new Date();

	@Getter
	@Setter
	@Column(name = "likeStatus", nullable = false, columnDefinition = "int default 0")
	private int aggreeDisAggreeStatus;
	
	@Getter
	@Setter
	@Column(name = "modeOfVote", nullable = false, columnDefinition = "int default 0")
	private int modeOfVote;
}