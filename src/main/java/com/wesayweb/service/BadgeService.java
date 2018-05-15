package com.wesayweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wesayweb.model.Badges;
import com.wesayweb.repository.BadgeRespository;

public class BadgeService {

	@Autowired
	BadgeRespository<Badges, ?> badgeRepository;
	
	public List<Badges> getListOfBadges(){
		return badgeRepository.findAll();
	}

}
