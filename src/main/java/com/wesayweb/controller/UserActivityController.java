package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.Badges;
import com.wesayweb.model.CommentLikeDislike;
import com.wesayweb.model.Comments;
import com.wesayweb.model.ContactList;
import com.wesayweb.model.Friends;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.UploadContacts;
import com.wesayweb.model.User;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.model.UserTrait;
import com.wesayweb.repository.CommentLikeDislikeRespository;
import com.wesayweb.repository.CommentRepository;
import com.wesayweb.repository.ContactRepository;
import com.wesayweb.repository.FriendsRepository;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.UploadContactRespository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.request.model.CommentOnTrait;
import com.wesayweb.request.model.ContactRequestModel;
import com.wesayweb.request.model.PhoneNumberModel;
import com.wesayweb.response.model.FriendsResponse;
import com.wesayweb.response.model.GenericApiResponse;
import com.wesayweb.response.model.MyFriendsZoneResponse;
import com.wesayweb.response.model.UserSettingResponse;
import com.wesayweb.service.AuthenticationService;
import com.wesayweb.service.BadgeService;
import com.wesayweb.service.EmailService;
import com.wesayweb.util.JwtSecurityUtil;
import com.wesayweb.validation.EntityValidation;

@RestController
@RequestMapping("/userzone")
public class UserActivityController {

	@Autowired
	SettingsRepository settingsRepositoryService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	CommentLikeDislikeRespository likeRespository;
	
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
	UploadContactRespository uploadContactRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationService authnticationService;

	@Autowired
	BadgeService badgeService;

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

				ContactList contactObj = contactRepository.getRequestedContactDetails(userObj.getMobilenumber(),
						userObj.getCountrycode(), loggedinuserObj.getId());
				contactRepository.save(contactObj);

				sendFriendRequestInEmail(userObj, "WeSay friend request", loggedinuserObj.getFullname());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/uploadContact/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse uploadContact(@RequestBody String contactList) {
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		UploadContacts contacts = UploadContacts.builder().build();
		parseContacts(contactList);
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);
		return responseObj;
	}

	public void parseContacts(String jsonString) {
		List<ContactRequestModel> contactList = new ArrayList<ContactRequestModel>();
		User loggedinUser = userRepositoryService.findActiveUser(authnticationService.getSessionUserId());
		Gson gson = new Gson();
		JsonArray jsonObject = gson.fromJson(jsonString, JsonArray.class);
		for (int counter = 0; counter < jsonObject.size(); counter++) {
			ContactRequestModel saltPojo = gson.fromJson(
					jsonObject.get(counter).getAsJsonObject().get("_objectInstance"), ContactRequestModel.class);
			contactList.add(saltPojo);
		}
		for (ContactRequestModel contactModel : contactList) {
			for (PhoneNumberModel phoneObj : contactModel.getPhoneNumbers()) {
				Map<String, String> phone = EntityValidation.getMobileNumberFromContact(phoneObj.getValue().trim(),
						loggedinUser.getCountrycode());
				if (phone.get("status").equalsIgnoreCase("valid")) {
					if (phoneObj.getValue().trim().length() > 8
							&& (!contactRepository.isAlreadyExists(phone.get("localnumber").trim(),
									phone.get("countrycode").trim(), authnticationService.getSessionUserId()))) {
						ContactList contactListObj = ContactList.builder().build();
						contactListObj.setFullname(contactModel.getDisplayName());
						contactListObj.setMobilenumber(phone.get("localnumber").trim());
						contactListObj.setCountrycode(phone.get("countrycode").trim().replace("+", ""));
						addContactToMylist(contactListObj);

					}
				}
			}
		}
	}

	public void addContactToMylist(ContactList contact) {
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		List<User> contactDetails = userRepositoryService.getUserByMobileEmail(contact.getCountrycode(),
				contact.getMobilenumber());
		if (contactDetails.size() > 0) {
			contact.setIsregistredinwesay(1);
		} else {
			contact.setIsregistredinwesay(0);
		}
		contact.setSourceuserid(logedinUserObj.getId());
		contactRepository.save(contact);
	}

	@RequestMapping(value = "/friendsZone/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse friendsZone() {
		GenericApiResponse responseObj = GenericApiResponse.builder().build();
		MyFriendsZoneResponse response = MyFriendsZoneResponse.builder().build();
		Map<String, List<FriendsResponse>> responseMap = new LinkedHashMap<String, List<FriendsResponse>>();
		response.setMyfriends(userRepositoryService.getMyConfirmedFriendList(authnticationService.getSessionUserId()));

		List<Friends> sentFriendRequest = friendsRepositoryService
				.getMySentFriendRequest(authnticationService.getSessionUserId());
		List<User> friendSentObj = new ArrayList<User>();
		for (Friends friendsObj : sentFriendRequest) {
			User userTempObj = new User();
			userTempObj.setFriendsid(friendsObj.getUser().getId());
			userTempObj.setId(friendsObj.getUser().getId());
			userTempObj.setFullname(friendsObj.getUser().getFullname());
			userTempObj.setEmailaddress(friendsObj.getUser().getEmailaddress());
			friendSentObj.add(userTempObj);
		}
		response.setMySentfriendrequest(friendSentObj);

		List<Friends> recievedFriendRequest = friendsRepositoryService
				.getMyRecievedFriendRequest(authnticationService.getSessionUserId());

		List<User> friendUserObj = new ArrayList<User>();
		for (Friends friendsObj : recievedFriendRequest) {
			User userTempObj = new User();
			userTempObj.setFriendsid(friendsObj.getFriendUser().getId());
			userTempObj.setId(friendsObj.getFriendUser().getId());
			userTempObj.setFullname(friendsObj.getFriendUser().getFullname());
			userTempObj.setEmailaddress(friendsObj.getFriendUser().getEmailaddress());
			friendUserObj.add(userTempObj);
		}

		response.setMyRecievedfriendrequest(friendUserObj);
		response.setMyContactList(contactRepository.getMyContactList(authnticationService.getSessionUserId()));
		responseObj.setResponse(response);
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);
		return responseObj;

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
		Map<String, String> customResponse = new LinkedHashMap<String, String>();
		customResponse.put("number_of_badges", String.valueOf(badgeService.getEligibleNumberOfBadeges().intValue()));
		responseObj.setResponse(badgeService.getAvailableBadges());
		responseObj.setStatus(WeSayContants.CONST_SUCCESS);
		responseObj.setCustomResponse(customResponse);
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
					contact.getMobilenumber());
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

	@RequestMapping(value = "/traits/comment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Object> commentOnTrait(@RequestBody CommentOnTrait comment) {
		GenericApiResponse returnValue = GenericApiResponse.builder().build();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		Comments commentObj = new Comments();
		commentObj.setCommentactivestatus(1);
		commentObj.setTraitId(new UserTrait(comment.getTraitId()));
		commentObj.setCommentedby(new User(logedinUserObj.getId()));
		commentObj.setCommentText(comment.getComment());
		commentObj.setDeletestatus(0);
		commentObj.setParent(new Comments(comment.getParentCommentId()));
		commentRepository.save(commentObj);
		returnValue.setStatus(WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/traits/comment/list", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Object> getCommentsOnTrait(@RequestBody CommentOnTrait comment) {
		GenericApiResponse returnValue = GenericApiResponse.builder().build();
		commentRepository.findByTraitId(comment.getTraitId());
		returnValue.setStatus(WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/traits/comment/like", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Object> likeOnComment(@RequestBody CommentOnTrait comment) {
		GenericApiResponse returnValue = GenericApiResponse.builder().build();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		
		CommentLikeDislike likeObj = likeRespository.findBylikeDislikeByAndComment(logedinUserObj.getId(), comment.getCommentId());
		likeObj.setLikeDislikeStatus(1);
		likeRespository.save(likeObj);
		returnValue.setStatus(WeSayContants.CONST_SUCCESS);
		return returnValue;
	}

	@RequestMapping(value = "/traits/comment/dislike", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GenericApiResponse<Object> dislikeOnComment(@RequestBody CommentOnTrait comment) {
		GenericApiResponse returnValue = GenericApiResponse.builder().build();
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		CommentLikeDislike likeObj = likeRespository.findBylikeDislikeByAndComment(logedinUserObj.getId(), comment.getCommentId());
		likeObj.setLikeDislikeStatus(2);
		likeRespository.save(likeObj);
		returnValue.setStatus(WeSayContants.CONST_SUCCESS); 
		return returnValue;
	}

	
	
	@RequestMapping(value = "/acceptfriendrequest/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> acceptfriendrequest(@RequestBody List<Friends> requestid) {
		System.err.println(requestid);
		Map<String, String> returnValue = new HashMap<String, String>();
		JwtSecurityUtil tokenUtil = new JwtSecurityUtil();
		for (Friends friendsrequestid : requestid) {
			List<Friends> friendsRequest = friendsRepositoryService
					.getMyFriendRequest(authnticationService.getSessionUserId(), friendsrequestid.getId());

			Friends friendobj = new Friends();
			if (friendsRequest.size() > 0) {
				friendobj = friendsRequest.get(0);
				friendobj.setActivestatus(1);
				friendobj.setInvitationacceptdate(new Date());
				friendobj.setInvitationacceptstatus(1);
				friendsRepositoryService.save(friendobj);
				// }
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

	public List<FriendsResponse> getMyListOfActiveFriends() {
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

}