package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserSettingsCategoryMapping;

@Repository
public interface UserSettingCustomRepository {

	@Query(value = "SELECT A.categoryname, B.categoryvalue , A.uniqueid FROM  wesayweb.settings_category A JOIN  wesayweb.user_settings_category_mapping B ON A.uniqueid = B.uniqueid"
			+ " WHERE B.userid = :userid", nativeQuery = true, name = "getmysettings")

	List<Object[]> getMySettings(@Param("userid") long userid);

	List<UserSettingsCategoryMapping> getUserSettings(Long userid);

	boolean changeMySetting(UserSettingsCategoryMapping settingObj);

}