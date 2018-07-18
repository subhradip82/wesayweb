package com.wesayweb.request.model;

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
public class PhoneNumberModel {

	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String pref;

	@Getter
	@Setter
	private String value;

	@Getter
	@Setter
	private String type;

}
