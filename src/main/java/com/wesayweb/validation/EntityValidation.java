package com.wesayweb.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.WhitespaceRule;

public class EntityValidation {

	final static String DATE_FORMAT = "dd/MM/yyyy";

	public static boolean isValidPartOfName(String name) {
		return name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
	}

	public static boolean isValidEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	public static boolean isValidCellnumber(String cellnumber, String regionCode) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			PhoneNumber phonenumber = phoneUtil.parse(cellnumber,
					phoneUtil.getRegionCodeForCountryCode(Integer.parseInt(regionCode)));
			PhoneNumberUtil.PhoneNumberType phoneNumberType = phoneUtil.getNumberType(phonenumber);
			return phoneNumberType == PhoneNumberUtil.PhoneNumberType.MOBILE;

		} catch (NumberParseException e) {
			return false;
		}
	}

	public static boolean isValidFullName(String fullname) {
		Pattern pattern = Pattern.compile(
				"^(?!(?:.*?\\.){2})(?!(?:.*?'){2})(?!(?:.*?,){2})(?!(?:.*?-){2})[a-zA-Z .',-]+$",
				Pattern.CASE_INSENSITIVE);
		return pattern.matcher(fullname).find();

	}

	public static boolean isValidGender(String gender) {
		if (gender.trim().length() < 1) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isValidDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String isValidPassword(String password) {
		LengthRule lengthRule = new LengthRule(8, 10);
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		charRule.getRules().add(new DigitCharacterRule(1));
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		charRule.getRules().add(new LowercaseCharacterRule(1));
		charRule.setNumberOfCharacteristics(3);
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		ruleList.add(charRule);
		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(password));
		RuleResult result = validator.validate(passwordData);
		if (validator.getMessages(result).size() == 0) {
			return "";
		} else {

			return validator.getMessages(result).get(0);
		}

	}
	 
}
