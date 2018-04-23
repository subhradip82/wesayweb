package com.wesayweb.validation;

import java.util.HashMap;
import java.util.Map;

import com.wesayweb.model.User;

public class UserLoginByEmailValidation {

	private User user;

	public UserLoginByEmailValidation(User user) {
		super();
		this.user = user;
	}

	public Map<String, String> validateUerLoginByEmail() {
		Map<String, String> validationResult = new HashMap<String, String>();
		if (!EntityValidation.isValidEmail(user.getEmailaddress())) {
			validationResult.put("email", "Please enter a valid Email Address.");
		}
		if (EntityValidation.isValidPassword(user.getPassword()).trim().length() > 1) {
			validationResult.put("passowrd", EntityValidation.isValidPassword(user.getEmailaddress()));
		}
		return validationResult;
	}

}
