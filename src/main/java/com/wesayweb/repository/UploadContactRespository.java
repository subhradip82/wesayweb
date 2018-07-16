package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UploadContacts;

@Repository
public interface UploadContactRespository extends JpaRepository<UploadContacts, Long> {

}