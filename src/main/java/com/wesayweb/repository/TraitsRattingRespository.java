package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.TraitRattings;

@Repository
public interface TraitsRattingRespository extends JpaRepository<TraitRattings, Long>, TraitsRattingCustomRespository {

	
}