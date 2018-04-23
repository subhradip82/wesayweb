package com.wesayweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesayweb.model.FirstImpression;
import com.wesayweb.repository.FirstImpressionRepository;

@RestController
@RequestMapping("/firstimpression")
public class FirstImpressionController {

	@Autowired
	FirstImpressionRepository impressionTraitsRepository;

	@RequestMapping(value = "/getFirstImpression/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<FirstImpression> getFirstImpression(@RequestBody FirstImpression impressionObj) {
		return impressionTraitsRepository.getUserFirstImpressions(impressionObj.getImpressiongivenby(),
				impressionObj.getImpressiongivenfor());
	}

	@RequestMapping(value = "/giveFirstImpression/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void giveFirstImpression(@RequestBody FirstImpression impressionObj) {
		impressionTraitsRepository.save(impressionObj);
	}

}