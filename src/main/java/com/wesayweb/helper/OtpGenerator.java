package com.wesayweb.helper;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class OtpGenerator {

	@Inject
	private static Logger logger;
	
	public static String genrateOtp() {
		String generatedOtp = "";
		try {
			TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
			final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
			keyGenerator.init(512);
			SecretKey secretKey = keyGenerator.generateKey();
			Date later = new Date(new Date().getTime() + TimeUnit.SECONDS.toMillis(600));
			generatedOtp = String.valueOf(totp.generateOneTimePassword(secretKey, later));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			logger.error(e.getMessage());
		}
		return generatedOtp;

	}
}
 