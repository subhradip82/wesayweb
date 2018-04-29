package com.wesayweb.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class WesayStringUtil {

	public static String generateRandomNumber() {
		String returnValue = "";
		try {
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			String randomNum = new Integer(prng.nextInt()).toString();
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] result = sha.digest(randomNum.getBytes());
			returnValue = hexEncode(result);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
		return returnValue;
	}

	static private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}

}
