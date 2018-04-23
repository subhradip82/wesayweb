package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.FirstImpression;

@Repository
public interface FirstImpressionRepository
		extends JpaRepository<FirstImpression, Long>, FirstImpressionCustomRepository {

}