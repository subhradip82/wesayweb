package com.wesayweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.wesayweb.constants.UserContants;
import com.wesayweb.helper.OtpGenerator;
import com.wesayweb.model.User;
import com.wesayweb.repository.UserRepository;
import com.wesayweb.service.EmailService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/mobileregistration/", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String registerviamobile(@RequestBody User user) {
		String returnValue = UserContants.CONST_MOBILE_ALREADY_EXISTS;
		if (userRepository.getUserByMobileNumber(user.getCountrycode().trim(), user.getMobilenumber().trim())
				.size() == 0) {
			userRepository.save(user);
			returnValue = UserContants.CONST_REGISTRATION_SUCCESSFUL;
		}
		return returnValue;
	}

	@RequestMapping(value = "/emailregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

	@ResponseBody
	public String registerviaemail(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		String returnValue = UserContants.CONST_EMAIL_ALREADY_EXISTS;
		if (userRepository.getUserByEmailAddess(user.getEmailaddress().trim()).size() == 0) {
			userRepository.save(user);
			returnValue = UserContants.CONST_REGISTRATION_SUCCESSFUL;
			sendotInemail(user);
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
	public String sendotInemail(@RequestBody User user) {
		String otp = OtpGenerator.genrateOtp();
		String emailSubject = "Dear User,\n\n\nPlease use the OTP : " + otp
				+ " to complete your registration.\nThe OTP is valid for 10 minutes";
		sendotptoemail(user.getEmailaddress(), "OTP From WeSayWEB", emailSubject);
		return otp;
	}

	public void sendotptoemail(String useremail, String subject, String message) {
		emailService.sendMail(useremail, subject, message);
	}

}