package com.wesayweb.response.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTraitsResponsePojo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Builder.Default
	private Long traitid = 0L;

	@Getter
	@Setter
	private String traitname;

	@Getter
	@Setter
	private String traituniqueid;

	@Getter
	@Setter
	private String traitdescripion;

	@Getter
	@Setter
	private String traiticonpath;

	@Getter
	@Setter
	private int positive;

	@Getter
	@Setter
	private int negetive;

	@Getter
	@Setter
	private int nutral;

	@Getter
	@Setter
	private String traittype;

	@Getter
	@Setter
	private int isannonymous;

	@Getter
	@Setter
	private int ishidden;
	
	
	@Getter
	@Setter
	@Default
	private int typeofvote;
	
	@Getter
	@Setter
	private String fullname;
	
	@Getter
	@Setter
	private String creationdate;
	
	
	
	
}