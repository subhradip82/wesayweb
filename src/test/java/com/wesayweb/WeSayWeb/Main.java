package com.wesayweb.WeSayWeb;

import java.util.Locale;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wesayweb.validation.EntityValidation;

public class Main {
	public static void main(String[] args) {

		String number = "+918237789378";
		System.err.println(EntityValidation.getMobileNumberFromContact(number, "+91"));

	}
}
