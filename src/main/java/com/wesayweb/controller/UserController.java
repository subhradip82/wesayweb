package com.wesayweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.UserContants;
import com.wesayweb.helper.OtpGenerator;
import com.wesayweb.model.User;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.request.model.UserRequest;
import com.wesayweb.service.EmailService;
import com.wesayweb.validation.UserRegistrationByEmailValidation;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/mobileregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String registerviamobile(@RequestBody UserRequest user) {
		String returnValue = UserContants.CONST_MOBILE_ALREADY_EXISTS;
		if (userRepository.getUserByMobileNumber(user.getCountrycode().trim(), user.getMobilenumber().trim())
				.size() == 0) {
			userRepository.save(getMappedUserObject(user));
			returnValue = UserContants.CONST_REGISTRATION_SUCCESSFUL;
		}
		return returnValue;
	}

	@RequestMapping(value = "/emailregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

	@ResponseBody
	public Map<String, String> registerviaemail(@RequestBody UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		UserRegistrationByEmailValidation validtionObj = new UserRegistrationByEmailValidation(user);
		Map<String, String> validationResult = validtionObj.validateUerRegistrationByEmail();
		if (validationResult.size() == 0) {
			if (userRepository.getUserByEmailAddess(user.getEmailaddress().trim()).size() == 0) {
				userRepository.save(getMappedUserObject(user));
				returnValue.put("message", UserContants.CONST_REGISTRATION_SUCCESSFUL);
				sendotInemail(user);
			} else {
				returnValue.put("message", UserContants.CONST_EMAIL_ALREADY_EXISTS);
			}
		} else {
			returnValue.putAll(validationResult);
		}

		return returnValue;
	}

	@RequestMapping(value = "/validatemobile/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public int validatebymobile(@RequestBody User user) {
		return userRepository.getUserByMobileNumber(user.getCountrycode(), user.getMobilenumber()).size();
	}

	@RequestMapping(value = "/validateemail/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public int validateemail(@RequestBody User user) {
		return userRepository.getUserByEmailAddess(user.getEmailaddress()).size();
	}

	@RequestMapping(value = "/sendotptoemail/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String sendotInemail(@RequestBody UserRequest user) {
		String otp = OtpGenerator.genrateOtp();
		String emailSubject = "Dear User,\n\n\nPlease use the OTP : " + otp
				+ " to complete your registration.\nThe OTP is valid for 10 minutes";
		sendotptoemail(user.getEmailaddress(), "OTP From WeSayWEB", emailSubject);
		return otp;
	}

	public void sendotptoemail(String useremail, String subject, String message) {
		emailService.sendMail(useremail, subject, message);
	}

	public User getMappedUserObject(UserRequest userReqObj) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User userObj = new User();
		userObj.setCountrycode(userReqObj.getCountrycode());
		userObj.setDateofbirth(userReqObj.getDateofbirth());
		userObj.setEmailaddress(userReqObj.getEmailaddress());
		userObj.setFirstname(userReqObj.getFirstname());
		userObj.setLastname(userReqObj.getLastname());
		userObj.setGender(userReqObj.getGender());
		userObj.setIsregisteredbymobile(userReqObj.getIsregisteredbymobile());
		userObj.setPassword(passwordEncoder.encode(userReqObj.getPassword())); 
		userObj.setMobilenumber(userReqObj.getMobilenumber());
		return userObj;
	}

}