package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.Friends;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.User;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.FriendsRepository;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.response.model.UserSettingResponse;
import com.wesayweb.service.EmailService;
import com.wesayweb.util.JwtSecurityUtil;

@RestController
@RequestMapping("/userzone")
public class UserActivityController {

	@Autowired
	SettingsRepository settingsRepositoryService;

	@Autowired
	FriendsRepository friendsRepositoryService;

	@Autowired
	UserSettingRepository userSettingRepositoryService;

	@Autowired
	UserRepository userRepositoryService;

	@Autowired
	EmailService emailService;

	private JwtSecurityUtil tokenUtil = new JwtSecurityUtil();

	@RequestMapping(value = "/applydeafultsettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> applydeafultsettings(HttpServletRequest request) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		applyusersdefaultsettings(Long.valueOf(token.get("userid")));
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, jToken);
		return returnValue;
	}

	@RequestMapping(value = "/mysettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<UserSettingResponse> mysettings(HttpServletRequest request) {
		List<UserSettingResponse> responseObj = new ArrayList<UserSettingResponse>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		Long userid = Long.valueOf(token.get("userid"));
		List<Object[]> resultSet = userSettingRepositoryService.getMySettings(userid);
		for (Object[] object : resultSet) {
			UserSettingResponse userSettingResponse = new UserSettingResponse();
			userSettingResponse.setCategoryname(object[0].toString().trim());
			userSettingResponse.setCategoryvalue(Integer.valueOf(object[1].toString().trim()));
			userSettingResponse.setUniqueid(object[2].toString().trim());
			responseObj.add(userSettingResponse);
		}
		return responseObj;
	}

	public void applyusersdefaultsettings(Long userid) {
		List<SettingsCategory> settingsCategoryList = settingsRepositoryService.findAll();
		for (SettingsCategory settingsCategoryObj : settingsCategoryList) {
			UserSettingsCategoryMapping userMappingObj = new UserSettingsCategoryMapping();
			userMappingObj.setCategoryid(settingsCategoryObj.getId());
			userMappingObj.setUserid(userid);
			userMappingObj.setCategoryvalue(settingsCategoryObj.getDefaultvalue());
			userMappingObj.setUniqueid(settingsCategoryObj.getUniqueid());
			userSettingRepositoryService.save(userMappingObj);
		}

	}

	@RequestMapping(value = "/sendfriendrequest/", 
			method = RequestMethod.POST, 
			produces = "application/json", 
			consumes = "application/json")
	@ResponseBody
	public Map<String, String> addafriend(HttpServletRequest request, @RequestBody List<Friends> friendsObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		User loggedinuserObj = userRepositoryService.findActiveUser(Long.valueOf(token.get("userid")));
		for (Friends friends : friendsObj) {
			Friends requestFriendObj = new Friends();
			User userObj = userRepositoryService.findActiveUser(Long.valueOf(friends.getFriendsid()));
			try {
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setAddeddate(new Date());
				requestFriendObj.setInvitationacceptstatus(0);
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setInvitedby(Long.valueOf(token.get("userid")));
				requestFriendObj.setFriendsid(friends.getFriendsid());
				requestFriendObj
						.setRequestuniueid(tokenUtil.createJWTTokenForFriendRequest(token.get("userid").toString(),
								userObj.getEmailaddress(), String.valueOf(friends.getFriendsid())));
				friendsRepositoryService.save(requestFriendObj);
				sendFriendRequestInEmail(userObj, "WeSay friend request", loggedinuserObj.getFullname());
			} catch (NullPointerException e) {

			}
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, jToken);
		return returnValue;
	}

	@RequestMapping(value = "/checkfriendrequest/", 
			method = RequestMethod.POST, 
			produces = "application/json", 
			consumes = "application/json")
	@ResponseBody
	public Map<String, String> checkfriendrequest(HttpServletRequest request) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		List<Friends> friendsRequest =  friendsRepositoryService.getMyFriendRequest(Long.valueOf(token.get("userid")));
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, String.valueOf(friendsRequest.size()));
		
		return returnValue;
	}
	

	@RequestMapping(value = "/acceptfriendrequest/", 
			method = RequestMethod.POST, 
			produces = "application/json", 
			consumes = "application/json")
	@ResponseBody
	public Map<String, String> acceptfriendrequest(HttpServletRequest request) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		List<Friends> friendsRequest =  friendsRepositoryService.getMyFriendRequest(Long.valueOf(token.get("userid")));
		for(Friends friendobj : friendsRequest) {
			friendobj.setActivestatus(1);
			friendobj.setInvitationacceptdate(new Date());
			friendobj.setInvitationacceptstatus(1);
			friendsRepositoryService.save(friendobj);
		}
		
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, String.valueOf(friendsRequest.size()));
		
		return returnValue;
	}
	public boolean sendFriendRequestInEmail(User user, String subject, String fullname) {
		String message = "Dear User,\n\n\nYou have recieved a friend request from  : " + fullname;
		return emailService.sendMail(user.getEmailaddress(), subject, message);
	}
}