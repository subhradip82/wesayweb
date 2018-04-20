package com.wesayweb.util;

import org.apache.commons.validator.routines.EmailValidator;

public class UserRegistrationValidation extends Object {

	private EmailValidator emailValidator = EmailValidator.getInstance();

	public boolean validateEmailAddress(String emailAddress) {

		return this.emailValidator.isValid(emailAddress);
	}

	public boolean validatePartOfName(String partOfName) {

		return partOfName.matches("[A-Z][a-zA-Z]");
	}

}