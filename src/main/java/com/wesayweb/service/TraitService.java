package com.wesayweb.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.FriendsRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.repository.UserTraitRepository;
import com.wesayweb.response.model.GenericApiResponse;
import com.wesayweb.response.model.TraitListResponse;
import com.wesayweb.util.SettingsUtil;

public class TraitService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserSettingRepository userSettingRepository;

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserTraitRepository userTraitsRepository;

	public GenericApiResponse<List<UserTrait>> traitsWiatingForApproval(Long userId)
	{
		List<UserTrait> userTraits =  userTraitsRepository.traitsWaitingForApproval(userId);
		GenericApiResponse genericResponseObj = GenericApiResponse.builder().build();
		Map<String, String> customResponse = new LinkedHashMap<String, String>();
		genericResponseObj.setStatus(WeSayContants.CONST_SUCCESS);
		customResponse.put("number_of_traits", String.valueOf(userTraits.size()));
		genericResponseObj.setCustomResponse(customResponse);
		genericResponseObj.setResponse(userTraits);
		return genericResponseObj;
	}
	
}
