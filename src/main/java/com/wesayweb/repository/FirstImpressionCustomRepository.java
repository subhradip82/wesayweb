package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.FirstImpression;

@Repository
public interface FirstImpressionCustomRepository {
	List<FirstImpression> getUserFirstImpressions(Long givenby, Long givenfor);


}
