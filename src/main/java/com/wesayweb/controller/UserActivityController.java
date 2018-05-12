package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
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
import com.wesayweb.response.model.UserSettingResponse;
import com.wesayweb.service.AuthnticationService;
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
	AuthnticationService authnticationService;

	@RequestMapping(value = "/applydeafultsettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> applydeafultsettings() {
		Map<String, String> returnValue = new HashMap<String, String>();
		applyusersdefaultsettings(authnticationService.getSessionUserId());
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/mysettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<UserSettingResponse> mysettings() {
		List<UserSettingResponse> responseObj = new ArrayList<UserSettingResponse>();
		List<Object[]> resultSet = userSettingRepositoryService.getMySettings(authnticationService.getSessionUserId());
		for (Object[] object : resultSet) {
			UserSettingResponse userSettingResponse = new UserSettingResponse();
			userSettingResponse.setCategoryname(object[0].toString().trim());
			userSettingResponse.setCategoryvalue(Integer.valueOf(object[1].toString().trim()));
			userSettingResponse.setUniqueid(object[2].toString().trim());
			responseObj.add(userSettingResponse);
		}
		return responseObj;
	}

	@RequestMapping(value = "/changemysettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<UserSettingResponse> changemysettings(@RequestBody UserSettingsCategoryMapping settingObj) {
		List<UserSettingResponse> responseObj = new ArrayList<UserSettingResponse>();
		settingObj.setUserid(authnticationService.getSessionUserId());
		userSettingRepositoryService.changeMySetting(settingObj);
		return responseObj;
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

	@RequestMapping(value = "/sendfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addafriend(@RequestBody List<Friends> friendsObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		JwtSecurityUtil tokenUtil = new JwtSecurityUtil();
		User loggedinuserObj = authnticationService.getSessionUser();
		for (Friends friends : friendsObj) {
			Friends requestFriendObj = new Friends();
			User userObj = userRepositoryService.findActiveUser(Long.valueOf(friends.getFriendsid()));
			try {
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setAddeddate(new Date());
				requestFriendObj.setInvitationacceptstatus(0);
				requestFriendObj.setActivestatus(0);
				requestFriendObj.setUserid(loggedinuserObj.getId());
				requestFriendObj.setInvitedby(loggedinuserObj.getId());
				requestFriendObj.setFriendsid(friends.getFriendsid());
				requestFriendObj.setRequestuniueid(
						tokenUtil.createJWTTokenForFriendRequest(String.valueOf(loggedinuserObj.getId()),
								userObj.getEmailaddress(), String.valueOf(friends.getFriendsid())));
				friendsRepositoryService.save(requestFriendObj);
				sendFriendRequestInEmail(userObj, "WeSay friend request", loggedinuserObj.getFullname());
			} catch (NullPointerException e) {

			}
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/checkfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> checkfriendrequest(HttpServletRequest request) {
		Map<String, String> returnValue = new HashMap<String, String>();
		List<Friends> friendsRequest = friendsRepositoryService
				.getMyFriendRequest(authnticationService.getSessionUserId());
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_NEW_FRIENDS_REQUEST, String.valueOf(friendsRequest.size()));
		return returnValue;
	}

	@RequestMapping(value = "/getMyContacts/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<ContactList> getMyContacts() {
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		return contactRepository.findAll();
	}

	@RequestMapping(value = "/addContacts/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addContacts(@RequestBody List<ContactList> contactList) {
		Map<String, String> returnValue = new HashMap<String, String>();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());

		for (ContactList contact : contactList) {
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
				Map<String, String> requesttoken = tokenUtil.parseInvitationJWT(friendobj.getRequestuniueid());
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
}