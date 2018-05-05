package com.wesayweb.controller;

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
import com.wesayweb.helper.OtpGenerator;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.User;
import com.wesayweb.model.UserOtp;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.UserOtpRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.request.model.UserRequest;
import com.wesayweb.service.EmailService;
import com.wesayweb.util.JwtSecurityUtil;
import com.wesayweb.util.PasswordEncrypterUtil;
import com.wesayweb.validation.UserLoginByEmailValidation;
import com.wesayweb.validation.UserRegistrationByEmailValidation;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	UserOtpRepository otpRepositoryService;
	
	@Autowired
	SettingsRepository settingsRepositoryService;

	@Autowired
	UserSettingRepository userSettingRepositoryService;


	private JwtSecurityUtil tokenUtil = new JwtSecurityUtil();

	@RequestMapping(value = "/emailregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> registerviaemail(@RequestBody UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		UserRegistrationByEmailValidation validtionObj = new UserRegistrationByEmailValidation(user);
		Map<String, String> validationResult = validtionObj.validateUerRegistrationByEmail();
		if (validationResult.size() == 0) {
			returnValue.putAll(completeRegistartion(user));
		} else {
			returnValue.putAll(validationResult);
		}
		return returnValue;
	}

	@RequestMapping(value = "/resendactivationcode/",
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> resendactivationcode(@RequestBody UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		User logedinUserObj = userRepository.findByUsername(user.getEmailaddress().trim().toLowerCase());
		String userOtp = otpRepositoryService.resendOtp(logedinUserObj.getId());
		sendotpInemail(user.getEmailaddress().trim().toLowerCase(), logedinUserObj.getId(), userOtp);
		String authToken = generateAuthToken(logedinUserObj);
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_OTP_SENT);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, authToken);
		return returnValue;
	}

	@RequestMapping(value = "/validateotpviaemail/", 
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> validateOtpViaemail(HttpServletRequest request, @RequestBody UserOtp userOtpObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		List<UserOtp> otpObj = otpRepositoryService.validateOtp(userOtpObj.getOtp(), 
								Long.valueOf(token.get("userid")));
		 
		if (otpObj.size() > 0) {
			if (new Date().compareTo(otpObj.get(0).getValidupto()) > 0) {
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE, "OTP expired");
			} else {
				userRepository.activateUser(Long.valueOf(token.get("userid")));
				otpRepositoryService.updateOtpStatus(Long.valueOf(token.get("userid")), userOtpObj.getOtp());
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
				returnValue.put(WeSayContants.CONST_AUTH_TOKEN, jToken);
				applyusersdefaultsettings(Long.valueOf(token.get("userid")));
				
			}
		} else {
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
			returnValue.put(WeSayContants.CONST_MESSAGE, "Wrong OTP provided");
		}
		return returnValue;

	}

	@RequestMapping(value = "/forgotpasswordviaemail/", 
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> forgotpasswordviaemail(@RequestBody UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		UserRegistrationByEmailValidation validtionObj = new UserRegistrationByEmailValidation(user);
		Map<String, String> validationResult = validtionObj.forgotpasswordByEmail();
		if (validationResult.size() == 0) {
			List<User> userObj = userRepository.getUserByEmailAddess(user.getEmailaddress().trim().toLowerCase(), 0);
			if (userObj.size() > 0) {
				sendForgotPasswordotpInemail(userObj.get(0).getEmailaddress(), userObj.get(0).getId());
				String authToken = generateAuthToken(userObj.get(0));
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
				returnValue.put(WeSayContants.CONST_AUTH_TOKEN, authToken);
				returnValue.put(WeSayContants.CONST_MESSAGE, "Otp sent");
			} else {
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE, "Email ID not found");
			}
		} else {
			returnValue.putAll(validationResult);
		}
		return returnValue;

	}

	
	@RequestMapping(value = "/loginviaemail/", 
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> loginviaemail(@RequestBody User user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		UserLoginByEmailValidation validtionObj = new UserLoginByEmailValidation(user);
		Map<String, String> validationResult = validtionObj.validateUerLoginByEmail();
		if (validationResult.size() == 0) {
			List<User> userObj = userRepository.getUserByEmailAddess(user.getEmailaddress().trim().toLowerCase(), 1);
			if (userObj.size() == 0) {
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_USER_NOT_ACTIVATED);
			} else if (PasswordEncrypterUtil.matches(user.getPassword().trim(), userObj.get(0).getPassword())) {
				String authToken = generateAuthToken(userObj.get(0));
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
				returnValue.put(WeSayContants.CONST_AUTH_TOKEN, authToken);

			} else {
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_WRONG_USER_NAME_PASSWORD);
			}
		} else {
			returnValue.putAll(validationResult);
		}

		return returnValue;
	}

	@RequestMapping(value = "/passwordretrivevalidateotp/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> passwordretrivevalidateotp(HttpServletRequest request, @RequestBody UserOtp userOtpObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		List<UserOtp> otpObj = otpRepositoryService.validateOtp(userOtpObj.getOtp(), Long.valueOf(token.get("userid")));
		if (otpObj.size() > 0) {
			if (new Date().compareTo(otpObj.get(0).getValidupto()) > 0) {
				returnValue.put(WeSayContants.CONST_MESSAGE, "OTP is expired");
			} else {
				UserRegistrationByEmailValidation validtionObj = new UserRegistrationByEmailValidation(userOtpObj);
				Map<String, String> validationResult = validtionObj.changepasswordByEmail();
				if (validationResult.size() == 0) {
					userRepository.changeUserPassword(Long.valueOf(token.get("userid")),
							PasswordEncrypterUtil.encode(userOtpObj.getPassword()));
					otpRepositoryService.updateOtpStatus(Long.valueOf(token.get("userid")), userOtpObj.getOtp());
					returnValue.put("message", "User password changed successfully");
					returnValue.put("authtoken", jToken);
				} else {
					returnValue.put("message", "Invalid password / retype password");
				}
			}

		} else {
			returnValue.put("message", "Wrong OTP provided or OTP already used");
		}
		return returnValue;

	}

	@RequestMapping(value = "/validatemobile/",
					method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public int validatebymobile(@RequestBody User user) {
		return userRepository.getUserByMobileNumber(user.getCountrycode(), user.getMobilenumber(), 0).size();
	}

	@RequestMapping(value = "/validateemail/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public int validateemail(@RequestBody User user) {
		return userRepository.getUserByEmailAddess(user.getEmailaddress(), 0).size();
	}

	public String sendotpInemail(String emailAddress, Long userId) {
		String otp = OtpGenerator.genrateOtp();

		String emailSubject = "Dear User,\n\n\nPlease use the OTP : " + otp
				+ " to complete your registration.\nThe OTP is valid for 10 minutes";
		if (sendotpInemail(emailAddress, "OTP From WeSayWEB", emailSubject)) {
			UserOtp userOtpObj = new UserOtp();
			userOtpObj.setUserid(userId);
			userOtpObj.setOtp(otp);
			otpRepositoryService.save(userOtpObj);
		}
		return otp;
	}

	public String sendotpInemail(String emailAddress, Long userId, String otp) {
		String emailSubject = "Dear User,\n\n\nPlease use the OTP : " + otp
				+ " to complete your registration.\nThe OTP is valid for 10 minutes";
		if (sendotpInemail(emailAddress, "OTP From WeSayWEB", emailSubject)) {
			UserOtp userOtpObj = new UserOtp();
			userOtpObj.setUserid(userId);
			userOtpObj.setOtp(otp);
			otpRepositoryService.save(userOtpObj);
		}
		return otp;
	}

	public String sendForgotPasswordotpInemail(String emailAddress, Long userId) {
		String otp = OtpGenerator.genrateOtp();
		String emailSubject = "Dear User,\n\n\nPlease use the OTP : " + otp
				+ " to complete your password retrival.\nThe OTP is valid for 10 minutes";
		if (sendotpInemail(emailAddress, "OTP From WeSayWEB", emailSubject)) {
			UserOtp userOtpObj = new UserOtp();
			userOtpObj.setUserid(userId);
			userOtpObj.setOtp(otp);
			otpRepositoryService.save(userOtpObj);
		}
		return otp;
	}

	public boolean sendotpInemail(String useremail, String subject, String message) {
		return emailService.sendMail(useremail, subject, message);
	}

	public User getMappedUserObject(UserRequest userReqObj) {
		User userObj = new User();
		userObj.setCountrycode(userReqObj.getCountrycode());
		userObj.setDateofbirth(userReqObj.getDateofbirth());
		userObj.setEmailaddress(userReqObj.getEmailaddress());
		userObj.setFullname(userReqObj.getFullname());
		userObj.setGender(userReqObj.getGender());
		userObj.setIsregisteredbymobile(userReqObj.getIsregisteredbymobile());
		userObj.setPassword(PasswordEncrypterUtil.encode(userReqObj.getPassword()));
		userObj.setMobilenumber(userReqObj.getMobilenumber());
		return userObj;
	}

	public String generateAuthToken(User userObj) {

		return tokenUtil.createJWT(String.valueOf(userObj.getId()), userObj.getEmailaddress(),
				userObj.getCountrycode() + userObj.getMobilenumber());

	}

	
	public Map<String, String> completeRegistartion(UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		if (userRepository.getUserByEmailAddess(user.getEmailaddress().trim(), 0).size() > 0) {
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
			returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_EMAIL_ALREADY_EXISTS);
		}
		if (userRepository.getUserByMobileNumber(user.getCountrycode(), user.getMobilenumber(), 0).size() > 0) {
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
			returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_MOBILE_ALREADY_EXISTS);
		}
		if (returnValue.size() == 0) {
			User insertedUserObj = userRepository.save(getMappedUserObject(user));
			sendotpInemail(insertedUserObj.getEmailaddress(), insertedUserObj.getId());
			String authToken = generateAuthToken(insertedUserObj);
			returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
			returnValue.put(WeSayContants.CONST_MESSAGE, WeSayContants.CONST_REGISTRATION_SUCCESSFUL);
			returnValue.put("authtoken", authToken);
		}
		return returnValue;
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

}