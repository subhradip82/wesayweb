package com.wesayweb.WeSayWeb;

import java.util.Locale;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.wesayweb.validation.EntityValidation;

public class Main {
	public static void main(String[] args) throws NumberParseException {

		String number = "+918237789378";
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(number, Locale.US.getCountry());
		System.err.println(phoneNumber.getCountryCode());

	}
}
