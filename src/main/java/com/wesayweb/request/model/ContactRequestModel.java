package com.wesayweb.request.model;

import java.util.List;

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
public class ContactRequestModel {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String rawId;

	@Getter
	@Setter
	private String displayName;

	@Getter
	@Setter
	private List<PhoneNumberModel> phoneNumbers;

}