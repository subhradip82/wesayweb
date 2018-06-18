package com.wesayweb.request.model;

import java.util.List;

import com.wesayweb.model.Badges;
import com.wesayweb.model.Badges.BadgesBuilder;

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
public class ContactRequest {

	@Getter
	@Setter
	private List<String> emailAddress;
	
	@Getter
	@Setter
	private List<String> mobileNumber;
	
	
	private String fullname;
	
	@Getter
	@Setter
	private boolean ifAvailableInWesay;
}