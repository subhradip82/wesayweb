package com.wesayweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wesayweb.repository.BadgeRespository;
import com.wesayweb.repository.ContactRepository;
import com.wesayweb.request.model.ContactRequest;



public class CotactService {

	@Autowired
	ContactRepository  contactRepository;

	public List<ContactRequest> checkIfContactIsInWesay(List<ContactRequest> contact) {
		List<ContactRequest> response = new ArrayList<ContactRequest>();
		for(ContactRequest contactRequestObj  : contact) {	
		for(String mobile  : contactRequestObj.getMobileNumber()) {
			for(String emailid : contactRequestObj.getEmailAddress()) {
					if(contactRepository.isAlreadyAddedInApplication(emailid, mobile))
					{
						contactRequestObj.setIfAvailableInWesay(true);
					}
				
			}
			response.add(contactRequestObj);
		}
		}
		return response;
	}

}
