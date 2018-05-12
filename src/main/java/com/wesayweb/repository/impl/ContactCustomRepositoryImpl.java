package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.repository.query.Param;

import com.wesayweb.model.ContactList;
import com.wesayweb.repository.ContactCustomRepository;

public class ContactCustomRepositoryImpl implements ContactCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	


	@Override
	public boolean saveMyContact(ContactList contact) {
		
		
		return false;
	}



	@Override
	public List<ContactList> getMyContactList(long sourceuserid) {
		return entityManager.createNamedQuery("getMyContactList").getResultList();
		 
	}

}
