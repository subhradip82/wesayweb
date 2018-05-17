package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.Badges;
import com.wesayweb.model.ContactList;
import com.wesayweb.model.Friends;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.User;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.ContactRepository;
import com.wesayweb.repository.FriendsRepository;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.response.model.FriendsResponse;
import com.wesayweb.response.model.GenericApiResponse;
import com.wesayweb.response.model.UserSettingResponse;
import com.wesayweb.service.AuthenticationService;
import com.wesayweb.service.BadgeService;
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

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationService authnticationService;
	
	@Autowired
	BadgeService badgeService;

	@RequestMapping(value = "/applydeafultsettings/", 
					method = RequestMethod.POST, 
					produces = "application/json",
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> applydeafultsettings() {
		Map<String, String> returnValue = new HashMap<String, String>();
		applyusersdefaultsettings(authnticationService.getSessionUserId());
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/mysettings/", 
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<List<UserSettingResponse>> mysettings() {
		List<UserSettingResponse> responseObj = new ArrayList<UserSettingResponse>();
		List<Object[]> resultSet = userSettingRepositoryService.getMySettings(authnticationService.getSessionUserId());
		
		
		for (Object[] object : resultSet) {
			UserSettingResponse userSettingResponse = new UserSettingResponse();
			userSettingResponse.setCategoryname(object[0].toString().trim());
			userSettingResponse.setCategoryvalue(Integer.valueOf(object[1].toString().trim()));
			userSettingResponse.setUniqueid(object[2].toString().trim());
			responseObj.add(userSettingResponse);
		}
		GenericApiResponse returnResponse = GenericApiResponse.builder().build();
		returnResponse.setResponse(WeSayContants.CONST_SUCCESS);
		returnResponse.setResponse(responseObj);
		return returnResponse;
	}

	@RequestMapping(value = "/changemysettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<UserSettingResponse> changemysettings(@RequestBody UserSettingsCategoryMapping settingObj) {
		List<UserSettingResponse> responseObj = new ArrayList<UserSettingResponse>();
		settingObj.setUserid(authnticationService.getSessionUserId());
		userSettingRepositoryService.changeMySetting(settingObj);
		return responseObj;
	}

	@RequestMapping(value = "/sendfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addafriend(@RequestBody List<Friends> friendsObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		JwtSecurityUtil tokenUtil = new JwtSecurityUtil();
		User loggedinuserObj = authnticationService.getSessionUser();
		for (Friends friends : friendsObj) {

			Friends requestFriendObj = Friends.builder().build();
			User userObj = userRepositoryService.findActiveUser(Long.valueOf(friends.getFriendsid()));

			try {
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setInvitationacceptstatus(0);
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setUserid(loggedinuserObj.getId());
				requestFriendObj.setInvitedby(loggedinuserObj.getId());
				requestFriendObj.setFriendsid(friends.getFriendsid());
				requestFriendObj.setRequestuniqueid(
						tokenUtil.createJWTTokenForFriendRequest(String.valueOf(loggedinuserObj.getId()),
								userObj.getEmailaddress(), String.valueOf(friends.getFriendsid())));
				friendsRepositoryService.save(requestFriendObj);
				sendFriendRequestInEmail(userObj, "WeSay friend request", loggedinuserObj.getFullname());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/checkfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> checkfriendrequest() {
		Map<String, String> returnValue = new HashMap<String, String>();
		List<Friends> friendsRequest = friendsRepositoryService
				.getMyFriendRequest(authnticationService.getSessionUserId());
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_NEW_FRIENDS_REQUEST, String.valueOf(friendsRequest.size()));
		return returnValue;
	}

	@RequestMapping(value = "/myFriends/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Friends> myFriends() {

		return friendsRepositoryService.getMyFriendRequest(authnticationService.getSessionUserId());

	}

	@RequestMapping(value = "/getMyFriendList/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<FriendsResponse> getMyFriendList() {
		List<FriendsResponse> returnList = new ArrayList<FriendsResponse>();
		List<Object[]> resultSet = friendsRepositoryService.getMyFriendList(authnticationService.getSessionUserId());
		for (Object[] object : resultSet) {
			FriendsResponse responseObj = FriendsResponse.builder().build();
			responseObj.setFriendsid(Long.valueOf(object[0].toString()));
			responseObj.setEmailaddress(object[1].toString());
			responseObj.setMobilenumber(object[3].toString());
			responseObj.setCountrycode(object[2].toString());
			responseObj.setFullname(object[4].toString());
			responseObj.setAddeddate(object[5].toString());
			if (null != object[6]) {
				responseObj.setInvitationacceptdate(object[6].toString());
			} else {
				responseObj.setInvitationacceptdate("N/A");
			}
			responseObj.setAccept_status(Integer.valueOf(object[7].toString()));
			responseObj.setId(Long.valueOf(object[8].toString()));
			returnList.add(responseObj);
		}
		return returnList;
	}

	@RequestMapping(value = "/getMyContacts/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<ContactList> getMyContacts() {
		return contactRepository.getMyContactList(authnticationService.getSessionUserId());
	}

	@RequestMapping(value = "/getMyBadgeCount/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Integer> getMyBadgeCount() {
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		responseObj.setResponse(badgeService.getEligibleNumberOfBadeges().intValue());
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);   
		return responseObj;
	}
	
	@RequestMapping(value = "/getAvailableBadges/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<List<Badges>> getAvailableBadges() {
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		responseObj.setResponse(badgeService.getAvailableBadges());
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);    
		return responseObj;
	}
	
	@RequestMapping(value = "/addCustomBadge/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Badges> addCustomBadge(Badges badgeObj) {
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		responseObj.setResponse(badgeService.getAvailableBadges());
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);    
		return responseObj;
	}
	
	
	@RequestMapping(value = "/addContacts/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addContacts(@RequestBody List<ContactList> contactList) {
		Map<String, String> returnValue = new HashMap<String, String>();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());

		for (ContactList contact : contactList) {
			List<User> contactDetails = userRepositoryService.getUserByMobileEmail(contact.getCountrycode(),
					contact.getMobilenumber(), contact.getEmailaddress());
			if (contactDetails.size() > 0) {
				contact.setIsregistredinwesay(1);
			} else {
				contact.setIsregistredinwesay(1);
			}
			contact.setSourceuserid(logedinUserObj.getId());
			contactRepository.save(contact);
		}
		return returnValue;
	}

	@RequestMapping(value = "/sendInvitationToJoinWeSay/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> sendInvitationToJoinWeSay(@RequestBody List<ContactList> contactList) {
		Map<String, String> returnValue = new HashMap<String, String>();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());

		for (ContactList contact : contactList) {
			contact.setSourceuserid(logedinUserObj.getId());
			contactRepository.save(contact);
		}
		return returnValue;
	}

	@RequestMapping(value = "/acceptfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> acceptfriendrequest(@RequestBody List<Friends> requestid) {
		Map<String, String> returnValue = new HashMap<String, String>();
		JwtSecurityUtil tokenUtil = new JwtSecurityUtil();
		for (Friends friendsrequestid : requestid) {
			List<Friends> friendsRequest = friendsRepositoryService
					.getMyFriendRequest(authnticationService.getSessionUserId(), friendsrequestid.getId());
			Friends friendobj = new Friends();
			if (friendsRequest.size() > 0) {
				friendobj = friendsRequest.get(0);
				Map<String, String> requesttoken = tokenUtil.parseInvitationJWT(friendobj.getRequestuniqueid());
				if (authnticationService.getSessionUser().getEmailaddress().trim()
						.equalsIgnoreCase(requesttoken.get("recieversemail"))
						&& (friendobj.getUserid() == Long.valueOf((requesttoken.get("sendersid"))))) {
					friendobj.setActivestatus(1);
					friendobj.setInvitationacceptdate(new Date());
					friendobj.setInvitationacceptstatus(1);
					friendsRepositoryService.save(friendobj);
				}
			}
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	public boolean sendIvitationEmailToJoinWesay(String userName, String invitationFrom, String email, String subject,
			String fullname) {
		String message = "Dear " + userName + ",\n\n\nYou have recieved request to join WeSay from  : "
				+ invitationFrom;
		return emailService.sendMail(email, subject, message);
	}

	public boolean sendFriendRequestInEmail(User user, String subject, String fullname) {
		String message = "Dear User,\n\n\nYou have recieved a friend request from  : " + fullname;
		return emailService.sendMail(user.getEmailaddress(), subject, message);
	}


	public void applyusersdefaultsettings(Long userid) {
		List<SettingsCategory> settingsCategoryList = settingsRepositoryService.findAll();
		for (SettingsCategory settingsCategoryObj : settingsCategoryList) {
			UserSettingsCategoryMapping userMappingObj = UserSettingsCategoryMapping.builder().build();
			userMappingObj.setCategoryid(settingsCategoryObj.getId());
			userMappingObj.setUserid(userid);
			userMappingObj.setCategoryvalue(settingsCategoryObj.getDefaultvalue());
			userMappingObj.setUniqueid(settingsCategoryObj.getUniqueid());
			userSettingRepositoryService.save(userMappingObj);
		}

	}

}