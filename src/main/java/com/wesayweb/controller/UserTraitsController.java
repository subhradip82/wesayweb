package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.model.Traits;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.TraitsResponsePojo;

@RestController
@RequestMapping("/userTraits")
public class UserTraitsController {

	@Autowired
	UserTraitRepository userTraitsRepository;

	@Autowired
	TraitRepository traitRepository;

	@RequestMapping(value = "/addTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void addTraits(@RequestBody UserTrait userTrait) {
		UserTrait userTraitObj = new UserTrait();
		userTraitObj.setTargetuserid(userTrait.getTargetuserid());
		userTraitObj.setTraitid(userTrait.getTraitid());
		userTraitObj.setGivenbyuserid(userTrait.getGivenbyuserid());
		userTraitObj.setIsannonymous(userTrait.getIsannonymous());
		userTraitObj.setTypeofvote(userTrait.getTypeofvote());
		userTraitsRepository.save(userTraitObj);
	}

	@RequestMapping(value = "/getMySelfTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<TraitsResponsePojo> getMySelfTraits(@RequestBody UserTrait userTrait) {
		List<UserTrait> userTraits = userTraitsRepository.getMyTraits(userTrait.getGivenbyuserid(),
				userTrait.getTargetuserid());
		List<Traits> allTraits = traitRepository.getActiveTraits();
		List<TraitsResponsePojo> traitResponse = new ArrayList<TraitsResponsePojo>();

		for (Traits trait : allTraits) {
			for (UserTrait usertrait : userTraits) {
				TraitsResponsePojo traitsResponseobj = new TraitsResponsePojo();
				traitsResponseobj.setAnnonimouststatus(usertrait.getIsannonymous());
				traitsResponseobj.setTraitdescripion(trait.getTraitdescripion());
				traitsResponseobj.setTraiticonpath(trait.getTraiticonpath());
				traitsResponseobj.setTraitid(trait.getId());
				traitsResponseobj.setTraitname(trait.getTraitname());
				traitsResponseobj.setUserid(usertrait.getTargetuserid());
				if (trait.getId() == usertrait.getId()) {
					traitsResponseobj.setTraitmarkstatus(1);
				} else {
					traitsResponseobj.setTraitmarkstatus(0);
				}
				traitResponse.add(traitsResponseobj);
			}
		}
		return traitResponse;
	}

	 
}