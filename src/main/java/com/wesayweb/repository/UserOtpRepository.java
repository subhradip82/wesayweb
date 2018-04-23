package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserOtp;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long>, UserOtpCustomRepository {

}