package com.wesayweb.repository;

import java.util.List;

import com.wesayweb.model.TraitsAggreeDisAggree;

 

public interface VoteOnTraitRespositoryCustom {

	public List<TraitsAggreeDisAggree> getVoteList(String traitIdentifier, Long userTraitId);
	public List<TraitsAggreeDisAggree> getMyVoteList(String traitIdentifier, Long userTraitId, Long userId);
	
}
