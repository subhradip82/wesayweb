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
public class RatingOnTrait implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String traitIdentifier;

	@Getter
	@Setter
	private Long userTraitId;

	
	@Getter
	@Setter
	private int rating;

}
