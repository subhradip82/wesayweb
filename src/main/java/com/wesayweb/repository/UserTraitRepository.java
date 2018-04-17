package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserTrait;

@Repository
public interface UserTraitRepository extends JpaRepository<UserTrait, Long>,UserTraitCustomRepository {

}