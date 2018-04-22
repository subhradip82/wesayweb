package com.wesayweb.validation;

import java.util.HashMap;
import java.util.Map;

import com.wesayweb.model.User;
import com.wesayweb.request.model.UserRequest;

public class UserRegistrationByEmailValidation {

	private UserRequest user;

	public UserRegistrationByEmailValidation(UserRequest user) {
		super();
		this.user = user;
	}

	public Map<String, String> validateUerRegistrationByEmail() {
		Map<String, String> validationResult = new HashMap<String, String>();
		if (!EntityValidation.isValidName(user.getFirstname())) {
			validationResult.put("firstname", "Please enter a valid first name.");
		}
		if (!EntityValidation.isValidName(user.getLastname())) {
			validationResult.put("firstname", "Please enter a valid last name.");
		}
		if (!EntityValidation.isValidDate(user.getDateofbirth())) {
			validationResult.put("dob", "Please enter a valid Date of Birth.");
		}
		if (!EntityValidation.isValidEmail(user.getEmailaddress())) {
			validationResult.put("email", "Please enter a valid Email Address.");
		}
		if (EntityValidation.isValidPassword(user.getPassword()).trim().length() > 1) {
			validationResult.put("passowrd", EntityValidation.isValidPassword(user.getEmailaddress()));
		}
		if (!EntityValidation.isValidCellnumber(user.getMobilenumber(),user.getCountrycode().trim())) {
			validationResult.put("mobile", "Please enter a valid mobile number");
		}

		return validationResult;
	}

}
