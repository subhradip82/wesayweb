package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.User;

@Repository
public interface UserCustomRepository {

	public List<User> getUserByEmailAddess(String emailaddress);

	List<User> getUserByMobileNumber(String countryCode, String mobileNumber);

}