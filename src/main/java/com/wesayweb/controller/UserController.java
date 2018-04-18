package com.wesayweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wesayweb.consatants.UserContants;
import com.wesayweb.model.User;
import com.wesayweb.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/mobileregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String registerviamobile(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		String returnValue = UserContants.CONST_REGISTRATION_SUCCESSFUL;
		if (userRepository.getUserByMobileNumber(user.getCountrycode().trim(), user.getMobilenumber().trim())
				.size() == 0) {
			userRepository.save(user);
			returnValue = UserContants.CONST_MOBILE_ALREADY_EXISTS;
		}
		return returnValue;
	}

	@RequestMapping(value = "/emailregistration/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

	@ResponseBody
	public String registerviaemail(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		String returnValue = UserContants.CONST_REGISTRATION_SUCCESSFUL;
		if (userRepository.getUserByEmailAddess(user.getEmailaddress().trim()).size() == 0) {
			userRepository.save(user);
			returnValue = UserContants.CONST_EMAIL_ALREADY_EXISTS;
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

}