package com.wesayweb.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/addTraits/", 
					method = RequestMethod.POST,
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public void addTraits(@RequestBody UserTrait userTrait) {
		userTraitsRepository.save(userTrait);
	}

	@RequestMapping(value = "/getMyTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<TraitsResponsePojo> getMyTraits(@RequestBody UserTrait userTrait) {
		List<TraitsResponsePojo> returnResult = new ArrayList<TraitsResponsePojo>();
		List<Object[]> resultSet = userTraitsRepository.
				getMyTraits(userTrait.getTraitgivenfor());
		for (Object[] object : resultSet) {

			TraitsResponsePojo traitsResponseObj = new TraitsResponsePojo();
			traitsResponseObj.setTraitid(((BigInteger) object[0]).longValue());
			traitsResponseObj.setTraitname((String) object[1]);
			traitsResponseObj.setTraitdescripion((String) object[2]);
			traitsResponseObj.setTraiticonpath((String) object[3]);
			traitsResponseObj.setPositive(((BigInteger) object[4]).intValue());
			traitsResponseObj.setNegetive(((BigInteger) object[5]).intValue());
			traitsResponseObj.setNutral(((BigInteger) object[6]).intValue());
			traitsResponseObj.setIsannonymous(((BigInteger) object[6]).intValue());
			returnResult.add(traitsResponseObj);
		}
		return returnResult;
	}

}