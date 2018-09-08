package com.wesayweb.response.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class AggreeDisAggreeResponsePojo implements Serializable { 
	 
	private static final long serialVersionUID = 1L;
	
	private int aggreeCount;
	private int publicAggreeCount;
	private int annonymousAggreeCount;
	 
	
	private int disAggreeCount;
	private int publicdisAggreeCount;
	private int annonymousdisAggreeCount;
	
	 	private List<CommentResponseUserPojo> aggreeDisAggree;
}
