package com.wesayweb.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.Badges;
import com.wesayweb.model.CustomBadges;
import com.wesayweb.repository.BadgeRespository;

public class BadgeService {

	@Autowired
	BadgeRespository badgeRepository;
	@Autowired
	AuthenticationService authnticationService;

	public List<Badges> getListOfBadges() {
		return badgeRepository.findAll();
	}

	public void uploadBadges() {
		badgeRepository.deleteAll();
		badgeRepository.saveAll(CsvReader.getBadges());
	}

	public CustomBadges addCustomBadge(CustomBadges badgesObj)
	{
		
		return null;
	}
	public BigInteger getEligibleNumberOfBadeges() {
		List<BigInteger> result = badgeRepository.getNumberOfEligibleBadges(authnticationService.getSessionUserId());
		return result.get(0);
	}

	public List<Badges> getAvailableBadges() {
		List<Badges> response = new ArrayList<Badges>();
		List<BigInteger> result = badgeRepository.getNumberOfEligibleBadges(authnticationService.getSessionUserId());
		if (result.get(0).intValue() > 0) {
			response = badgeRepository.getAvailableBadges();
		}
		return response;
	}

}
