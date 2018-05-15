package com.wesayweb.response.model;

import java.io.Serializable;
import java.util.Map;

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
public class GenericApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String status;

	@Getter
	@Setter
	private String authtoken;

	@Getter
	@Setter
	private T response;

	@Getter
	@Setter
	private Map<String,String> customResponse;
}