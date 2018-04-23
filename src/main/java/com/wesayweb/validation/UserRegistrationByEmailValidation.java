package com.wesayweb.validation;

import java.util.HashMap;
import java.util.Map;

import com.wesayweb.model.User;
import com.wesayweb.model.UserOtp;
import com.wesayweb.request.model.UserRequest;

public class UserRegistrationByEmailValidation {

	private UserRequest user;

	private UserOtp userOtpObj;
	
	public UserRegistrationByEmailValidation(UserRequest user) {
		super();
		this.user = user;
	}
	
	public UserRegistrationByEmailValidation(UserOtp userOtpObj) {
		super();
		this.userOtpObj = userOtpObj;
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
		if (!user.getPassword().trim().equals(user.getRetypepassword().trim())) {
			validationResult.put("retype", "Password and Retype password should match");
		}
		return validationResult;
	}


	public Map<String, String> forgotpasswordByEmail() {
		Map<String, String> validationResult = new HashMap<String, String>();
		
		if (!EntityValidation.isValidEmail(user.getEmailaddress())) {
			validationResult.put("email", "Please enter a valid Email Address.");
		}
		 
		return validationResult;
	}
	public Map<String, String> changepasswordByEmail() {
		Map<String, String> validationResult = new HashMap<String, String>();
		
		 
		if (EntityValidation.isValidPassword(userOtpObj.getPassword()).trim().length() > 1) {
			validationResult.put("passowrd", EntityValidation.isValidPassword(userOtpObj.getPassword()));
		}
		if (!userOtpObj.getPassword().trim().equals(userOtpObj.getRetypepassword().trim())) {
			validationResult.put("retype", "Password and Retype password should match"); 
		}
		return validationResult;
	}
	
}
