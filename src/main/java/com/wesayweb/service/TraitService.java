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
import com.wesayweb.response.model.UserTraitsResponsePojo;
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

	public GenericApiResponse<List<UserTraitsResponsePojo>> traitsWiatingForApproval(Long userId)
	{
		List<Object[]> resultSet =  userTraitsRepository.traitsWaitingForApproval(userId);
		List<UserTraitsResponsePojo> responseList = new ArrayList<UserTraitsResponsePojo>();
		for (Object[] object : resultSet) {
			UserTraitsResponsePojo responseObj = UserTraitsResponsePojo.builder().build();
			responseObj.setTraituniqueid(object[0].toString());
			responseObj.setFullname(object[1].toString());
			responseObj.setTraitname(object[2].toString());
			responseObj.setTraiticonpath(object[3].toString());
			responseObj.setTraittype(object[4].toString());
			responseObj.setTypeofvote(Integer.valueOf(object[5].toString()));
			responseObj.setCreationdate(object[6].toString());
				responseList.add(responseObj);
		}
		
		GenericApiResponse genericResponseObj = GenericApiResponse.builder().build();
		Map<String, String> customResponse = new LinkedHashMap<String, String>();
		genericResponseObj.setStatus(WeSayContants.CONST_SUCCESS);
		customResponse.put("number_of_traits", String.valueOf(responseList.size()));
		genericResponseObj.setCustomResponse(customResponse);
		genericResponseObj.setResponse(responseList);
		return genericResponseObj;
	}
	
}
