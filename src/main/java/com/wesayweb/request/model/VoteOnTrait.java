package com.wesayweb.request.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteOnTrait implements Serializable {

	 
	@Getter
	@Setter
	private Long traitId;

	@Getter
	@Setter
	private String traitIdentifier;

	@Getter
	@Setter
	private int vote;

	@Getter
	@Setter
	private int modeOfVote;

	
}
