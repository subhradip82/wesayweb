package com.wesayweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.Traits;
import com.wesayweb.repository.TraitRepository;

@RestController
@RequestMapping("/adminapi")
public class TraitsController {

	@Autowired
	TraitRepository traitsRepository;

	@RequestMapping(value = "/uploadTraits/", method = RequestMethod.POST)
	@ResponseBody
	public void uploadTraits() {

		traitsRepository.saveAll(CsvReader.getTraits());
	}

	@RequestMapping(value = "/addTraitsByAdmin/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> addTraitByAdmin(@RequestBody List<Traits> listOfTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		for (Traits traitobj : listOfTrait) {
			if (!traitsRepository.traitAlreadyExists(traitobj.getTraitname().trim().toLowerCase())) {
				traitsRepository.save(traitobj);
			} else {
				returnValue.put(traitobj.getTraitname(), " already exists");
			}
		}
		return returnValue;
	}


	@RequestMapping(value = "/updateTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> updateTrait(@RequestBody Traits traitObject) {
		Map<String, String> returnValue = new HashMap<String, String>();
		
			if (!traitsRepository.ifTraitIsUpdatable(traitObject)) {
				traitsRepository.save(traitObject);
				returnValue.put(traitObject.getTraitname(), " has been updated successfully");
			} else {
				returnValue.put(traitObject.getTraitname(), " can not be updated");
			}
		
		return returnValue;
	}

	
	@RequestMapping(value = "/getTraits/", method = RequestMethod.GET)
	@ResponseBody
	public List<Traits> getTraits() {
		return traitsRepository.getActiveTraits();
	}

}