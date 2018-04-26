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

import com.wesayweb.constants.WeSayContants;
import com.wesayweb.helper.CsvReader;
import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;
import com.wesayweb.repository.TraitRepository;
import com.wesayweb.service.EmailService;

@RestController
@RequestMapping("/traitapi")
public class TraitsController {

	@Autowired
	TraitRepository traitsRepository;

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/uploadTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void uploadTraits() {
		traitsRepository.saveAll(CsvReader.getTraits());
	}

	@RequestMapping(value = "/addCustomTrait/", method = RequestMethod.POST, 
					produces = "application/json", 
					consumes = "application/json")
	@ResponseBody
	public Map<String, String> addCustomTrait(@RequestBody CustomTraits customTraitObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
			if (!traitsRepository.traitAlreadyExists(customTraitObj.getTraitname(), 1L, 1L)) {
				traitsRepository.saveCustomTrait(customTraitObj);
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_SUCCESS);
			}
			else
			{
				returnValue.put(WeSayContants.CONST_STATUS, WeSayContants.CONST_ERROR);
				returnValue.put(WeSayContants.CONST_MESSAGE,"Trait already exists.");
			}
		return returnValue;
	}

	@RequestMapping(value = "/updateTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> updateTrait(@RequestBody Traits traitObject) {
		Map<String, String> returnValue = new HashMap<String, String>();
		if (!traitsRepository.ifTraitIsUpdatable(traitObject)) {
			traitsRepository.updateTrait(traitObject);
			returnValue.put(traitObject.getTraitname(), " has been updated successfully");
		} else {
			returnValue.put(traitObject.getTraitname(), " can not be updated");
		}
		return returnValue;
	}

	@RequestMapping(value = "/getActiveTraits/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Traits> getActiveTraits(@RequestBody Map<String, Integer> traityype) {
		return traitsRepository.getActiveTraits(traityype.get("traittype"));
	}

	@RequestMapping(value = "/hardDeleteTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> hardDeleteTrait(@RequestBody List<Traits> listOfTrait) {
		Map<String, String> returnValue = new HashMap<String, String>();
		for (Traits traitobj : listOfTrait) {
			traitsRepository.delete(traitobj);
		}
		return returnValue;
	}

	@RequestMapping(value = "/deleteTrait/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> deleteTrait(@RequestBody Traits traitObj) {
		Map<String, String> returnValue = new HashMap<String, String>();
		traitsRepository.deleteTrait(traitObj.getId());
		return returnValue;
	}

}