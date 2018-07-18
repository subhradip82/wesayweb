package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.User;

@Repository
public interface UserCustomRepository {

	public List<User> getUserByEmailAddess(String emailaddress, int activeStatus);
	public  List<User> getUserByMobileNumber(String countryCode, String mobileNumber, int activeStatus);
	public  List<User> getUserByMobileEmail(String countryCode, String mobileNumber); 
	boolean activateUser(Long userId);
	boolean changeUserPassword(Long userId, String password);
	User findByUsername(String emailaddress);
	User findActiveUser(Long userid);

}