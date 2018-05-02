package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.TraitListResponse;

@RestController
@RequestMapping("/traitapi")
public class TraitsController {

	@Autowired
	TraitRepository traitsRepository;

	@Autowired
	UserTraitRepository userTraitsRepository;
	
	@Autowired
	UserRepository userRepository;
	

	@RequestMapping(value = "/uploadTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void uploadTraits() {
		traitsRepository.saveAll(CsvReader.getTraits());
	}

	@RequestMapping(value = "/addTrait/",
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> addTrait(@RequestBody List<CustomTraits> listOfCustomTraitObj) {
		
		Map<String, String> returnValue = new HashMap<String, String>();
		for(CustomTraits customTraitObj : listOfCustomTraitObj) {
		User logedinUserObj = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		 if (traitsRepository.traitAlreadyExists(customTraitObj.getTraitname().trim().toLowerCase(), logedinUserObj.getId(), customTraitObj.getTraitgivenfor())
				.size() == 0) {
			 List<Traits> definedTraits = traitsRepository
					.definedTraitAlreadyExists(customTraitObj.getTraitname().trim().toLowerCase());
			UserTrait userTraitObj = new UserTrait();
			if (definedTraits.size() > 0) {
				userTraitObj.setTraitid(definedTraits.get(0).getId());
				userTraitObj.setTraituniqueid(definedTraits.get(0).getTraituniqueid());
				userTraitObj.setTraitgivenby(logedinUserObj.getId());
				userTraitObj.setTraitgivenfor(customTraitObj.getTraitgivenfor());
			} else {
				CustomTraits returnCustomTraitObj = traitsRepository.saveCustomTrait(customTraitObj);
				returnCustomTraitObj.setTraittype("nutral");
				userTraitObj.setTraitid(returnCustomTraitObj.getId());
				userTraitObj.setTraituniqueid(returnCustomTraitObj.getTraituniqueid());
				userTraitObj.setTraitgivenby(logedinUserObj.getId());
				userTraitObj.setTraitgivenfor(customTraitObj.getTraitgivenfor());
				
			}
			userTraitsRepository.saveUserTraits(userTraitObj);
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		}

		else {
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
			returnValue.put(WeSayContants.CONST_MESSAGE, "Trait already exists.");
		}
		}
		return returnValue;
	}

	@RequestMapping(value = "/getActiveTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, List<TraitListResponse>> getActiveTraits(@RequestBody Map<String, Integer> traityype) {
		Map<String, List<TraitListResponse>> listOfTraits = new LinkedHashMap<String, List<TraitListResponse>>();
		List<Traits> traitList = traitsRepository.getActiveTraits(0, 0);
		List<String> availableCategory = new ArrayList<String>();
		availableCategory.add("negative");
		availableCategory.add("neutral");
		availableCategory.add("positive");
		for (String traitName : availableCategory) {
			List<TraitListResponse> responseList = new ArrayList<TraitListResponse>();
			for (Traits traitobj : traitList) {
				if (traitName.trim().equalsIgnoreCase(traitobj.getTraittype().trim())) {
					TraitListResponse responseObj = new TraitListResponse();
					responseObj.setTraitname(traitobj.getTraitname().trim());
					responseObj.setTraiticonpath(traitobj.getTraitdescripion());
					responseObj.setTraituniqueid(traitobj.getTraituniqueid());
					responseList.add(responseObj);
				}
			}
			listOfTraits.put(traitName.trim().toLowerCase(), responseList);
		}

		return listOfTraits;

	}

	@RequestMapping(value = "/getListOfPoulerTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, List<TraitListResponse>> getListOfPoulerTraits() {
		Map<String, List<TraitListResponse>> listOfTraits = new LinkedHashMap<String, List<TraitListResponse>>();
		List<Traits> traitList = traitsRepository.getActiveTraits(2, 20);
		List<String> availableCategory = new ArrayList<String>();
		availableCategory.add("negative");
		availableCategory.add("neutral");
		availableCategory.add("positive");
		for (String traitName : availableCategory) {
			List<TraitListResponse> responseList = new ArrayList<TraitListResponse>();
			for (Traits traitobj : traitList) {
				if (traitName.trim().equalsIgnoreCase(traitobj.getTraittype().trim())) {
					TraitListResponse responseObj = new TraitListResponse();
					responseObj.setTraitname(traitobj.getTraitname().trim());
					responseObj.setTraiticonpath(traitobj.getTraitdescripion());
					responseObj.setTraituniqueid(traitobj.getTraituniqueid());
					responseList.add(responseObj);
				}
			}
			listOfTraits.put(traitName.trim().toLowerCase(), responseList);
		}

		return listOfTraits;

	}

	@RequestMapping(value = "/updateusertraitstatus/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> updateusertraitstatus(@RequestBody List<UserTrait> listOfUserTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		for (UserTrait traitobj : listOfUserTrait) {
			userTraitsRepository.updateUserTrait(traitobj);
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	 
}