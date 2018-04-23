package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

 

}