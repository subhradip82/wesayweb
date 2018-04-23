package com.wesayweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.wesayweb.model.UserOtp;
import com.wesayweb.repository.UserOtpRepository;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.request.model.UserRequest;
import com.wesayweb.service.EmailService;
import com.wesayweb.util.JwtSecurityUtil;
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

	private JwtSecurityUtil tokenUtil = new JwtSecurityUtil();


	@RequestMapping(value = "/emailregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

	@ResponseBody
	public Map<String, String> registerviaemail(@RequestBody UserRequest user) {
		Map<String, String> returnValue = new HashMap<String, String>();
		UserRegistrationByEmailValidation validtionObj = new UserRegistrationByEmailValidation(user);
		Map<String, String> validationResult = validtionObj.validateUerRegistrationByEmail();
		if (validationResult.size() == 0) {
			if (userRepository.getUserByEmailAddess(user.getEmailaddress().trim()).size() == 0) {
				User insertedUserObj = userRepository.save(getMappedUserObject(user));
				sendotpInemail(insertedUserObj.getEmailaddress(), insertedUserObj.getId());
				String authToken = generateAuthToken(insertedUserObj);
				returnValue.put("message", UserContants.CONST_REGISTRATION_SUCCESSFUL);
				returnValue.put("authtoken", authToken); 
				
			} else {
				returnValue.put("message", UserContants.CONST_EMAIL_ALREADY_EXISTS);
			}
		} else {
			returnValue.putAll(validationResult);
		}

		return returnValue;
	}

	public String generateAuthToken(User userObj)
	{
		
		return tokenUtil.createJWT(String.valueOf(userObj.getId()),userObj.getEmailaddress(), userObj.getCountrycode()+userObj.getMobilenumber() );
		
	}
	
	@RequestMapping(value = "/validateotp/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> validatebymobile(HttpServletRequest request, 
			@RequestBody UserOtp userOtpObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token =  tokenUtil.parseJWT(jToken);
		List<UserOtp> otpObj = otpRepositoryService.validateOtp(userOtpObj.getOtp(), Long.valueOf(token.get("userid")));
		if(otpObj.size()>0)
		{
			if(UserOtp.addMinutesToDate(10, otpObj.get(0).getCreationdate()).compareTo(otpObj.get(0).getValidupto()) < 0) {
				returnValue.put("message", "ÖTP is expired");
			}
			else
			{
				userRepository.activateUser(Long.valueOf(token.get("userid")));
				otpRepositoryService.updateOtpStatus(Long.valueOf(token.get("userid")), userOtpObj.getOtp());
				returnValue.put("message", "User account has been activated");
				returnValue.put("authtoken", jToken);

			}

		}
		else
		{
			returnValue.put("message", "Wrong ÖTP provided");
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

	public boolean sendotpInemail(String useremail, String subject, String message) {
		return emailService.sendMail(useremail, subject, message);
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