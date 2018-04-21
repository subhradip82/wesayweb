package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserTrait;

@Repository
public interface UserTraitCustomRepository   {

	public List<UserTrait> getMySelfTraits(long givenby, long tragetUerId);
	public List<UserTrait> getMyTraits(long tragetUerId);
	
}