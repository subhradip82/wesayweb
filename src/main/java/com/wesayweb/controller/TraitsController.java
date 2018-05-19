package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.FriendsRepository;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.GenericApiResponse;
import com.wesayweb.response.model.TraitListResponse;
import com.wesayweb.response.model.UserTraitsResponsePojo;
import com.wesayweb.service.AuthenticationService;
import com.wesayweb.service.TraitService;
import com.wesayweb.util.SettingsUtil;

@RestController
@RequestMapping("/traitapi")
public class TraitsController {

	@Autowired
	TraitRepository traitsRepository;

	@Autowired
	UserTraitRepository userTraitsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserSettingRepository userSettingRepository;

	@Autowired
	FriendsRepository friendsRepository;

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	TraitService traitService;

	
	@RequestMapping(value = "/addTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addTrait(@RequestBody List<CustomTraits> listOfCustomTraitObj) {
		SettingsUtil settingsUtil = new SettingsUtil();
		Map<String, String> returnValue = new HashMap<String, String>();
		User logedinUserObj = authenticationService.getSessionUser();
		for (CustomTraits customTraitObj : listOfCustomTraitObj) {
			
			int readstatus = 0;
			if (customTraitObj.getTraitgivenfor() == 0) {
				customTraitObj.setTraitgivenfor(logedinUserObj.getId());
				readstatus = 1;
			} else if (customTraitObj.getTraitgivenfor() == logedinUserObj.getId()) {
				customTraitObj.setTraitgivenfor(logedinUserObj.getId());
				readstatus = 1;
			} /*else if (!friendsRepository.areTheyFriends(logedinUserObj.getId(), customTraitObj.getTraitgivenfor())) {
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE, "Invalid request");
				return returnValue;
			}*/

			
			if (traitsRepository.traitAlreadyExists(customTraitObj.getTraitname().trim().toLowerCase(),
					logedinUserObj.getId(), customTraitObj.getTraitgivenfor()).size() == 0) {
			List<Traits> definedTraits = traitsRepository
						.definedTraitAlreadyExists(customTraitObj.getTraitname().trim().toLowerCase());
				UserTrait userTraitObj = new UserTrait();
				userTraitObj.setIsactive(1);
				if (definedTraits.size() > 0) {
					userTraitObj.setTraitid(definedTraits.get(0).getId());
					userTraitObj.setTraituniqueid(definedTraits.get(0).getTraituniqueid());
					userTraitObj.setTraitgivenby(logedinUserObj.getId());
					userTraitObj.setTraitgivenfor(customTraitObj.getTraitgivenfor());
					if (logedinUserObj.getId() != userTraitObj.getTraitgivenfor() && settingsUtil.isRuleAppliable(
							userSettingRepository.getUserSettings(userTraitObj.getTraitgivenfor()),
							"c25bfc6a4ef111e89c2dfa7ae01bbebc")) {
						userTraitObj.setIswaitingforapproval(1);
					} else {
						userTraitObj.setIswaitingforapproval(0);
					}

				} else {
					CustomTraits returnCustomTraitObj = traitsRepository.saveCustomTrait(customTraitObj);
					returnCustomTraitObj.setTraittype(WeSayContants.CONST_TRAIT_USER_TYPE);
					userTraitObj.setTraitid(returnCustomTraitObj.getId());
					userTraitObj.setTraituniqueid(returnCustomTraitObj.getTraituniqueid());
					userTraitObj.setTraitgivenby(logedinUserObj.getId());
					userTraitObj.setTraitgivenfor(customTraitObj.getTraitgivenfor());
					if (logedinUserObj.getId() != userTraitObj.getTraitgivenfor() && settingsUtil.isRuleAppliable(
							userSettingRepository.getUserSettings(userTraitObj.getTraitgivenfor()),
							"c25bfc6a4ef111e89c2dfa7ae01bbebc")) {
						userTraitObj.setIswaitingforapproval(1);
					} else {
						userTraitObj.setIswaitingforapproval(0);
					}

				}
				userTraitObj.setTypeofvote(customTraitObj.getTypeofvote());
				userTraitObj.setReadstatus(readstatus);
				userTraitsRepository.saveUserTraits(userTraitObj);
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
			}
			else
			{
				changeTraitForUser(customTraitObj);
			}
		}
		return returnValue;
	}

	public void changeTraitForUser(CustomTraits traitObj)
	{
		List<Traits> definedTraits = traitsRepository
				.definedTraitAlreadyExists(traitObj.getTraitname().trim().toLowerCase());
		if(definedTraits.size()>0) {
			traitsRepository.updateUserTrait(definedTraits.get(0).getTraituniqueid(),
					authenticationService.getSessionUserId(),
					traitObj.getTraitgivenfor(),traitObj.getTypeofvote());
			
		}
		else 
		{
			 List<String> result =	traitsRepository.customTraitAlreadyExists(traitObj.getTraitname().trim().toLowerCase(), 
					authenticationService.getSessionUserId(),
					traitObj.getTraitgivenfor());

			 for(String uniqueid : result) {
				 traitsRepository.updateCustomTrait(uniqueid, authenticationService.getSessionUserId(),
					traitObj.getTraitgivenfor(),traitObj.getTypeofvote());
			 }
			 
		}
	}
	@RequestMapping(value = "/getActiveTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse getActiveTraits() {
		List<Traits> traitList = traitsRepository.getActiveTraits(0, 0);
		List<TraitListResponse> responseList = new ArrayList<TraitListResponse>();
		for (Traits traitobj : traitList) {
			TraitListResponse responseObj = new TraitListResponse();
			responseObj.setTraitname(traitobj.getTraitname().trim());
			responseObj.setTraiticonpath(traitobj.getTraitdescripion());
			responseObj.setTraituniqueid(traitobj.getTraituniqueid());
			responseList.add(responseObj);
		}
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);
		responseObj.setResponse(responseList);
		return responseObj;
	}

	@RequestMapping(value = "/getListOfPoulerTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<List<TraitListResponse>> getListOfPoulerTraits() {
		List<Traits> traitList = traitsRepository.getActiveTraits(0, 20, 1);
		List<TraitListResponse> responseList = new ArrayList<TraitListResponse>();
		for (Traits traitobj : traitList) {
			TraitListResponse responseObj = new TraitListResponse();
			responseObj.setTraitname(traitobj.getTraitname().trim());
			responseObj.setTraiticonpath(traitobj.getTraitdescripion());
			responseObj.setTraituniqueid(traitobj.getTraituniqueid());
			responseList.add(responseObj);
		}
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);
		responseObj.setResponse(responseList);
		return responseObj;
	}

	@RequestMapping(value = "/deleteTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> deleteTrait(@RequestBody UserTrait userTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		if (userTrait.getTraitgivenfor() == 0) { // Its for self{
			userTrait.setTraitgivenfor(authenticationService.getSessionUserId());
		}
		userTraitsRepository.deleteTrait(userTrait);
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/hideTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> hideTrait(@RequestBody UserTrait userTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		userTrait.setTraitgivenfor(authenticationService.getSessionUserId());
		userTraitsRepository.hideTrait(userTrait);
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/unhideTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> unHideTrait(@RequestBody UserTrait userTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		userTrait.setTraitgivenfor(authenticationService.getSessionUserId());
		userTraitsRepository.unHideTrait(userTrait);
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue; 
	}

	@RequestMapping(value = "/traitswiatingforapproval/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<List<UserTraitsResponsePojo>> traitswiatingforapproval() {
		return traitService.traitsWiatingForApproval(authenticationService.getSessionUserId());
	}
	
	
	
	@RequestMapping(value = "/approvecustomtrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse approveCustomTrait(@RequestBody UserTrait userTrait) {
		GenericApiResponse returnValue = GenericApiResponse.builder().build();
		userTraitsRepository.approveCustomTrait(userTrait);
		returnValue.setStatus(WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/newtraitsarived/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> newtraitsarived() {
		Map<String, String> returnValue = new HashMap<String, String>();
		userTraitsRepository.listOfUnreadTrait(authenticationService.getSessionUserId());
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}
}