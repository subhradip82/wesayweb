package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserSettingsCategoryMapping;

@Repository
public interface UserSettingCustomRepository {

	List<UserSettingsCategoryMapping> getUserSettings(Long userid);

}