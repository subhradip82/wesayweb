package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.TraitRattings;

@Repository
public interface TraitsRattingCustomRespository {

	public List<TraitRattings> getTraitRating(Long userTraitId, Long userId);
	public List<TraitRattings> getTraitRating(Long userTraitId);
 	
}