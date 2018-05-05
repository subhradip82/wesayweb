package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.Friends;

@Repository
public interface FriendsCustomRepository {

	List<Friends> getMyFriendRequest(Long userid);
	List<Friends> getMyFriendRequest(Long userid, Long requestid);

}