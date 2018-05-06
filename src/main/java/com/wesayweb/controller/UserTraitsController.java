package com.wesayweb.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	@RequestMapping(value = "/getMyTraits/",
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public List<UserTraitsResponsePojo> getMyTraits(
			@RequestBody(required = false) User traitsgivenforUser) {
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		Long traitsgivenfor = 0L;
		boolean ismyowntrait = true;
		try {
			// Need to check if the user is friend or not
			traitsgivenfor = traitsgivenforUser.getId();
			ismyowntrait = false;
		} catch (Exception e) {
			traitsgivenfor = logedinUserObj.getId();
		}
		if (traitsgivenfor == logedinUserObj.getId()) {
			ismyowntrait = true;
		}
		List<Object[]> resultSet = userTraitsRepository.getMyTraits(traitsgivenfor);
			SettingsUtil settingsUtl = new SettingsUtil();
			List<UserTraitsResponsePojo> responseList = new ArrayList<UserTraitsResponsePojo>();
			for (Object[] object : resultSet) {
					UserTraitsResponsePojo traitsResponseObj = new UserTraitsResponsePojo();
					traitsResponseObj.setTraituniqid((String) object[0]);
					traitsResponseObj.setTraitname((String) object[1]);
					traitsResponseObj.setTraitdescripion((String) object[2]);
					traitsResponseObj.setTraiticonpath((String) object[3]);
					if (ismyowntrait || settingsUtl.isRuleAppliable(
							userSettingRepository.getUserSettings(traitsgivenfor),
							"c25bf9724ef111e89c2dfa7ae01bbebc")) {
						traitsResponseObj.setPositive(((BigInteger) object[4]).intValue());
						traitsResponseObj.setNegetive(((BigInteger) object[5]).intValue());
						traitsResponseObj.setNutral(((BigInteger) object[6]).intValue());
					} else {
						traitsResponseObj.setPositive(9999999);
						traitsResponseObj.setNegetive(9999999);
						traitsResponseObj.setNutral(9999999);
					}

					traitsResponseObj.setTraittype((String) object[7]);
					responseList.add(traitsResponseObj);
				}

					

		return responseList;
	}

	
	
}