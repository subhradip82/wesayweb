package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.TraitsAggreeDisAggree;

@Repository
public interface VoteOnTraitRespository
		extends JpaRepository<TraitsAggreeDisAggree, Long>, VoteOnTraitRespositoryCustom {
}