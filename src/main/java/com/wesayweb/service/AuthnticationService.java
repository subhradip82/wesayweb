package com.wesayweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wesayweb.model.User;
import com.wesayweb.repository.UserRepository;

public class AuthnticationService {

	@Autowired
	UserRepository userRepository;

	public Long getSessionUserId() {
		User logedinUserObj = userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		return logedinUserObj.getId();
	}

	public User getSessionUser() {
		return userRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName().trim().toLowerCase());
		 
	}
}
