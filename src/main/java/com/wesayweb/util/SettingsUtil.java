package com.wesayweb.util;

import java.util.List;

import com.wesayweb.model.UserSettingsCategoryMapping;

public class SettingsUtil {

	public boolean isRuleAppliable(List<UserSettingsCategoryMapping> usersetiings, String settingValue) {
		boolean returnvalue = false;
		for (UserSettingsCategoryMapping usersetting : usersetiings) {
			if (usersetting.getUniqueid().trim().equalsIgnoreCase(settingValue.trim())) {
				if (usersetting.getCategoryvalue() == 1) {
					returnvalue = true;
				}
			}
		}
		return returnvalue;
	}

}