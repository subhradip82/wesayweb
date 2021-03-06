package com.wesayweb.response.model;

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

public class TraitListResponse {

	@Getter
	@Setter
	private String traitname;

	@Getter
	@Setter
	private String traitdescription;

	@Getter
	@Setter
	private String traiticonpath;

	@Getter
	@Setter
	private String traituniqueid;

	@Getter
	@Setter
	private String message;

	@Getter
	@Setter
	private int readstatus;
}
