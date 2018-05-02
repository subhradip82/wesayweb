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

import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.model.UserOtp;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.TraitListResponse;
import com.wesayweb.response.model.UserTraitsResponsePojo;

@RestController
@RequestMapping("/userTraits")
public class UserTraitsController {

	@Autowired
	UserTraitRepository userTraitsRepository;

	@Autowired
	TraitRepository traitRepository;
	
	@Autowired
	UserRepository userRepository;
	
 
	@RequestMapping(value = "/getMyTraits/", 
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, List<UserTraitsResponsePojo>> getMyTraits() {
		User logedinUserObj = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		List<UserTraitsResponsePojo> returnResult = new ArrayList<UserTraitsResponsePojo>();
		List<Object[]> resultSet = userTraitsRepository.
				getMyTraits(logedinUserObj.getId());
		Map<String, List<UserTraitsResponsePojo>> listOfTraits = new LinkedHashMap<String, List<UserTraitsResponsePojo>>();
		 
		List<String> availableCategory = new ArrayList<String>();
		availableCategory.add("negative");
		availableCategory.add("neutral");
		availableCategory.add("positive");
		for (String traitName : availableCategory) {
			List<UserTraitsResponsePojo> responseList = new ArrayList<UserTraitsResponsePojo>();
			for (Object[] object : resultSet) {
			if (traitName.trim().equalsIgnoreCase(((String) object[7]).trim())) {
				

				UserTraitsResponsePojo traitsResponseObj = new UserTraitsResponsePojo();
				traitsResponseObj.setTraituniqid((String) object[0]); 
				traitsResponseObj.setTraitname((String) object[1]);
				traitsResponseObj.setTraitdescripion((String) object[2]);
				traitsResponseObj.setTraiticonpath((String) object[3]);
				traitsResponseObj.setPositive(((BigInteger) object[4]).intValue());
				traitsResponseObj.setNegetive(((BigInteger) object[5]).intValue());
				traitsResponseObj.setNutral(((BigInteger) object[6]).intValue());
				traitsResponseObj.setIsannonymous(((BigInteger) object[6]).intValue());
				traitsResponseObj.setTraittype((String) object[7]); 
				responseList.add(traitsResponseObj);
			}
			
			}
			listOfTraits.put(traitName.trim().toLowerCase(), responseList);
		}
		
		return listOfTraits; 
	}

}