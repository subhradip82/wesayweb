package com.wesayweb.request.model;

import java.util.List;

import javax.persistence.Column;

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
	@Column(name = "is_friend_request_sent", nullable = false, columnDefinition = "int default 0")
	private int is_friend_request_sent;

	
	@Getter
	@Setter
	private List<PhoneNumberModel> phoneNumbers;

}