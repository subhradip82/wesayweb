package com.wesayweb.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncrypterUtil {

	public static  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static String encode(String password)
	{
		
		return passwordEncoder.encode(password);
	}
	
	public static boolean matches(String primaryPassword, String secondaryPassword)
	{
		return passwordEncoder.matches(primaryPassword, secondaryPassword);
	}
}
