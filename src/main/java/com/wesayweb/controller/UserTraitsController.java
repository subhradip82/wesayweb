package com.wesayweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.model.Traits;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserTraitRepository;

@RestController
@RequestMapping("/userTraits")
public class UserTraitsController {

	@Autowired
	UserTraitRepository userTraitsRepository;
	
	@Autowired
	TraitRepository traitRepository;

	@RequestMapping(value = "/addSelfTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public void addMyTraits(@RequestParam long userid, @RequestParam long traitid) {
		UserTrait userTraitObj = new UserTrait();
		userTraitObj.setTargetuserid(userid);
		userTraitObj.setTraitid(traitid);
		userTraitObj.setGivenbyuserid(userid);
		userTraitObj.setIsannonymous(0);
		userTraitsRepository.save(userTraitObj);
	}

	@RequestMapping(value = "/giveTraitsToOthers/", method = RequestMethod.POST, produces = "application/json", consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public void giveTraitsToOthers(@RequestParam long givenby, @RequestParam long traitid,
			@RequestParam long targetUserId, @RequestParam int annonymousMode) {
		UserTrait userTraitObj = new UserTrait();
		userTraitObj.setTargetuserid(targetUserId);
		userTraitObj.setTraitid(traitid);
		userTraitObj.setGivenbyuserid(givenby);
		userTraitObj.setIsannonymous(annonymousMode);
		userTraitsRepository.save(userTraitObj);
	}

	@RequestMapping(value = "/getMySelfTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public void getMySelfTraits(@RequestParam long givenby) {
		List<UserTrait> userRelatedTraits = userTraitsRepository.getMySelfTraits(givenby);
		List<Traits> traitsList = traitRepository.getActiveTraits();
	
	}
}