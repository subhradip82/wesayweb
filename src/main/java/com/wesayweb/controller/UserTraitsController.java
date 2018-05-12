package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.model.User;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.UserTraitsResponsePojo;
import com.wesayweb.util.SettingsUtil;

@RestController
@RequestMapping("/userTraits")
public class UserTraitsController {

	@Autowired
	UserTraitRepository userTraitsRepository;

	@Autowired
	TraitRepository traitRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserSettingRepository userSettingRepository;

	@RequestMapping(value = "/getMyTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<UserTraitsResponsePojo> getMyTraits(@RequestBody(required = false) User traitsgivenforUser) {
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());

		Long traitsgivenfor = 0L;
		boolean ismyowntrait = true;

		try {
			if (traitsgivenforUser.getId() != 0) {
				// Need to check if the user is friend or not
				traitsgivenfor = traitsgivenforUser.getId();
				ismyowntrait = false;
			} else {
				traitsgivenfor = logedinUserObj.getId();
				ismyowntrait = true;

			}
		} catch (NullPointerException e) {
			traitsgivenfor = logedinUserObj.getId();
			ismyowntrait = true;

		}
		List<Object[]> resultSet = new ArrayList<Object[]>();
		if (ismyowntrait) {

			resultSet.addAll(userTraitsRepository.getMyTraits(traitsgivenfor));
		} else {
			resultSet.addAll(userTraitsRepository.getMyFriendsTraits(traitsgivenfor));
		}


		SettingsUtil settingsUtl = new SettingsUtil();
		List<UserTraitsResponsePojo> responseList = new ArrayList<UserTraitsResponsePojo>();
		for (Object[] object : resultSet) {
			 
			UserTraitsResponsePojo traitsResponseObj = new UserTraitsResponsePojo();
			traitsResponseObj.setTraituniqid((String) object[0]);
			traitsResponseObj.setTraitname((String) object[1]);
			traitsResponseObj.setTraiticonpath((String) object[2]);
			traitsResponseObj.setTraittype((String) object[3]);
			if (ismyowntrait || settingsUtl.isRuleAppliable(userSettingRepository.getUserSettings(traitsgivenfor),
					"c25bf9724ef111e89c2dfa7ae01bbebc")) {
				traitsResponseObj.setPositive(Integer.valueOf(object[4].toString()));
				traitsResponseObj.setNegetive(Integer.valueOf(object[5].toString()));
				traitsResponseObj.setNutral(Integer.valueOf(object[6].toString()));
			} else {
				traitsResponseObj.setPositive(99999);
				traitsResponseObj.setNegetive(99999);
				traitsResponseObj.setNutral(99999);
			}
		
			traitsResponseObj.setIshidden(Integer.valueOf(object[7].toString()));
			responseList.add(traitsResponseObj);
		}

		return responseList;
	}

}