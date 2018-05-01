package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserOtp;

@Repository
public interface UserOtpCustomRepository {

	 public boolean saveUserOtp(UserOtp userOtpObject);
	 public List<UserOtp> validateOtp(String otp, Long userid);
	 boolean updateOtpStatus(Long userId, String otp);
	 public String resendOtp(Long userid);
}