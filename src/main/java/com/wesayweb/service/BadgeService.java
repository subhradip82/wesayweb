package com.wesayweb.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.Badges;
import com.wesayweb.model.CustomBadges;
import com.wesayweb.model.UserBadgesCount;
import com.wesayweb.repository.BadgeRespository;
import com.wesayweb.repository.UserBadgeCountRepository;

public class BadgeService {

	private int badgecountforregistration = 3;

	@Autowired
	BadgeRespository badgeRepository;

	@Autowired
	AuthenticationService authnticationService;

	@Autowired
	UserBadgeCountRepository userBadgeCountRepository;

	public List<Badges> getListOfBadges() {
		return badgeRepository.findAll();
	}

	public void addBadgesForRegistration() {
		for (int count = 0; count < badgecountforregistration; count++) {
			UserBadgesCount userBadgeCountObj = UserBadgesCount.builder().build();
			userBadgeCountObj.setId(authnticationService.getSessionUserId());
			userBadgeCountObj.setBadgesfor("registration_successful");
			userBadgeCountRepository.save(userBadgeCountObj);
		}
	}

	public void uploadBadges() {
		badgeRepository.deleteAll();
		badgeRepository.saveAll(CsvReader.getBadges());
	}

	public CustomBadges addCustomBadge(CustomBadges badgesObj) {

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
