package com.wesayweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.UserSettingsCategoryMapping;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserSettingRepository;
import com.wesayweb.service.BadgeService;

@RestController
@RequestMapping("/adminzone")
public class AdminController {

	@Autowired
	SettingsRepository settingsRepositoryService; 

	@Autowired
	TraitRepository traitsRepository;

	@Autowired
	BadgeService badgeServiceObj;
	
	@Autowired
	UserSettingRepository userSettingRepositoryService;


	@RequestMapping(value = "/prepolulatesettingstable/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void prepolulatesettingstable() {

		CsvReader readerObj = new CsvReader();
		settingsRepositoryService.deleteAllInBatch();
		settingsRepositoryService.saveAll(readerObj.getSettings());
	}

	@RequestMapping(value = "/uploadTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void uploadTraits() {
		traitsRepository.removealltraits();
		traitsRepository.saveAll(CsvReader.getTraits());
	}

	@RequestMapping(value = "/uploadBadges/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void uploadBadges() {
		badgeServiceObj.uploadBadges();
	}

	@RequestMapping(value = "/applyDefaultSettingsForusers/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void applyDefaultSettingsForusers() {
		userSettingRepositoryService.deleteAll();
		 List<Long> userIds = new ArrayList<Long>();
		 userIds.add(2L);
		 userIds.add(3L);
		 userIds.add(4L);
		 userIds.add(5L);
		 userIds.add(6L);
		 userIds.add(8L);
		 for(Long userid : userIds) {
		List<SettingsCategory> settingsCategoryList = settingsRepositoryService.findAll();
		for (SettingsCategory settingsCategoryObj : settingsCategoryList) {
			UserSettingsCategoryMapping userMappingObj = UserSettingsCategoryMapping.builder().build();
			userMappingObj.setCategoryid(settingsCategoryObj.getId());
			userMappingObj.setUserid(userid);
			userMappingObj.setCategoryvalue(settingsCategoryObj.getDefaultvalue());
			userMappingObj.setUniqueid(settingsCategoryObj.getUniqueid());
			userSettingRepositoryService.save(userMappingObj);
		}
		 }
	}
}
