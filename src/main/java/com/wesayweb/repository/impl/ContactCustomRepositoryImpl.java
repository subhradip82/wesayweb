package com.wesayweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.repository.query.Param;

import com.wesayweb.model.ContactList;
import com.wesayweb.model.Friends;
import com.wesayweb.model.User;
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



	@Override
	public boolean isAlreadyAddedInApplication(String emailid, String mobilenum) {

		Criteria crit = entityManager.unwrap(Session.class).createCriteria(User.class);
		crit.add(Restrictions.eq("emailaddress", emailid));
		crit.add(Restrictions.eq("mobilenumber", mobilenum));
		 
		 if(crit.list().size()>0)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
		
		 
	}



	@Override
	public boolean getByMobilenumber(String mobilenumber, String countryCode) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(ContactList.class);
		crit.add(Restrictions.eq("mobilenumber", mobilenumber.trim()).ignoreCase());
		crit.add(Restrictions.eq("countrycode", countryCode.trim()).ignoreCase());
		 
		 if(crit.list().size()>0)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}



	@Override
	public ContactList getRequestedContactDetails(String mobileNumber, String countryCode , Long loggedinUserId) {
		ContactList contactDetailsObj = ContactList.builder().build();
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(ContactList.class);
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.trim()).ignoreCase());
		crit.add(Restrictions.eq("countrycode", countryCode.trim()).ignoreCase());
		crit.add(Restrictions.eq("sourceuserid", loggedinUserId));
		List<ContactList> contactList = crit.list();
		if(contactList.size()>0)
		 {
			contactDetailsObj = contactList.get(0);
			contactDetailsObj.setIsinviationsent(1);
			
		 }
		  
		
	
		return contactDetailsObj;
	}



	@Override
	public boolean isAlreadyExists(String mobileNumber, String countryCode, Long loggedinUserId) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(ContactList.class);
		crit.add(Restrictions.eq("mobilenumber", mobileNumber.trim()).ignoreCase());
		crit.add(Restrictions.eq("countrycode", countryCode.trim()).ignoreCase());
		crit.add(Restrictions.eq("sourceuserid", loggedinUserId));
		if(crit.list().size()>0)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}

}
