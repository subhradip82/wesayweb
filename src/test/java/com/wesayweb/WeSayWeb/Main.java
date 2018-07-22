package com.wesayweb.WeSayWeb;

import java.util.Locale;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Main {
	public static void main(String[] args) {

		String number = "+918806144030";
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		try {
			Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(number, Locale.US.getCountry());
			System.out.println(phoneNumber.getNationalNumber());
		} catch (NumberParseException e) {
			// error handling
		}

	}
}
