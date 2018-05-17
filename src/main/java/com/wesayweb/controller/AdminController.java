package com.wesayweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.repository.SettingsRepository;
import com.wesayweb.repository.TraitRepository;
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
}
