package com.wesayweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.Traits;
import com.wesayweb.model.User;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.repository.UserRepository;

@RestController
@RequestMapping("/adminapi")
public class TraitsController {

	@Autowired
	TraitRepository traitsRepository;

	@RequestMapping(value = "/addTraits/", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void addTraits() {
		traitsRepository.saveAll(CsvReader.getTraits());
		
	}
	
	@RequestMapping(value = "/getTraits/", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Traits> getTraits() {
		return traitsRepository.getActiveTraits();
	}
	

	
	
}