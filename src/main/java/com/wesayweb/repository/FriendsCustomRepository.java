package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Friends;

@Repository
public interface FriendsCustomRepository {

	List<Friends> getMySentFriendRequest(Long userid);
	List<Friends> getMyRecievedFriendRequest(Long userid);
	List<Friends> getMyFriendRequest(Long userid, Long requestid);
	boolean areTheyFriends(Long firstFriendId, Long secondFriendId);

	@Query(value = " WITH FRIENDS_DATA AS (" + "	   SELECT "
			+ "			CASE WHEN invitedby = :userid THEN friendsid ELSE invitedby END AS friendsid,"
			+ "			CASE WHEN invitedby = :userid AND invitationacceptstatus = 0 THEN '1' "
			+ "			WHEN invitedby != :userid AND invitationacceptstatus = 0 THEN '2'"
			+ "			else 3 END AS accept_status," + "			addeddate," + "			invitationacceptdate, id "
			+ "			FROM friend_list " + "			WHERE (invitedby = :userid OR friendsid = :userid)"
			+ "			)" + "			SELECT  A.friendsid," + "					B.emailaddress,"
			+ "					B.mobilenumber," + "					B.countrycode,"
			+ "					B.fullname ," + "					A.addeddate,"
			+ "					A.invitationacceptdate," + "					A.accept_status, A.id"
			+ "					"
			+ "			FROM  FRIENDS_DATA A JOIN user_master B  ON A.friendsid = B.id", nativeQuery = true, name = "getMyFriendList")
	public List<Object[]> getMyFriendList(@Param("userid") long userid);
	
	
}