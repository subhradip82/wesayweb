package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.ContactList;

@Repository
public interface ContactRepository extends JpaRepository<ContactList, Long>, ContactCustomRepository {

	public boolean getByMobilenumber(String mobilenumber);
}