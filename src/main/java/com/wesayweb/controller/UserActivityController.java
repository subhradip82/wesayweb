package com.wesayweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.util.JwtSecurityUtil;

@RestController
@RequestMapping("/userzone")
public class UserActivityController {

	@Autowired
	SettingsRepository settingsRepositoryService;

	@Autowired
	UserSettingRepository userSettingRepositoryService;

	private JwtSecurityUtil tokenUtil = new JwtSecurityUtil();

	@RequestMapping(value = "/applydeafultsettings/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> applydeafultsettings(HttpServletRequest request) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String jToken = request.getHeader("X-Authorization").trim();
		Map<String, String> token = tokenUtil.parseJWT(jToken);
		List<SettingsCategory> settingsCategoryList = settingsRepositoryService.findAll();
		for (SettingsCategory settingsCategoryObj : settingsCategoryList) {
			UserSettingsCategoryMapping userMappingObj = new UserSettingsCategoryMapping();
			userMappingObj.setCategoryid(settingsCategoryObj.getId());
			userMappingObj.setUserid(Long.valueOf(token.get("userid")));
			userMappingObj.setCategoryvalue(settingsCategoryObj.getDefaultvalue());
			userMappingObj.setUniqueid(settingsCategoryObj.getUniqueid());
			userSettingRepositoryService.save(userMappingObj);
		}
		returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
		returnValue.put(WeSayContants.CONST_AUTH_TOKEN, jToken);
		return returnValue;
	}

}