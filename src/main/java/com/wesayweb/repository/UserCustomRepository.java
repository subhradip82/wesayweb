package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.User;

@Repository
public interface UserCustomRepository {

	public List<User> getUserByEmailAddess(String emailaddress, int activeStatus);
	 
	List<User> getUserByMobileNumber(String countryCode, String mobileNumber, int activeStatus);
	 
	boolean activateUser(Long userId);
	boolean changeUserPassword(Long userId, String password);
	User findByUsername(String emailaddress);
	User findActiveUser(Long userid);

}