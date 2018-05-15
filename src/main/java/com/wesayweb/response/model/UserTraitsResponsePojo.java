package com.wesayweb.response.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	private Long traitid;

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
}