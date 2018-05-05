package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Friends;
import com.wesayweb.model.User;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> , FriendsCustomRepository {
	

}