package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserSettingsCategoryMapping;

@Repository
public interface UserSettingRepository
		extends JpaRepository<UserSettingsCategoryMapping, Long>, UserSettingCustomRepository {

}