package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.User;

@Repository
public interface UserCustomRepository {

	public List<User> getUserByEmailAddess(String emailaddress, int activeStatus);
	public  List<User> getUserByMobileNumber(String countryCode, String mobileNumber, int activeStatus);
	public  List<User> getUserByMobileNumberForUserSync(String countryCode, String mobileNumber, Long sessionUserId);
	public  List<User> getUserByMobileEmail(String countryCode, String mobileNumber); 
	boolean activateUser(Long userId);
	boolean changeUserPassword(Long userId, String password);
	User findByUsername(String emailaddress);
	User findActiveUser(Long userid);
	
	@Query(value = "select * from wesayweb.user_master where id in (   " + 
			"select case when userid = :userid then friendsid else userid end as userid "
			+ " from wesayweb.friend_list where  (userid = :userid or friendsid = :userid )   " + 
			"and invitationacceptstatus = 1 and requestuniqueid is not null and invitationacceptdate is not null   " + 
			"    )", nativeQuery = true, name = "getMyConfirmedFriendList")
	public List<User> getMyConfirmedFriendList(@Param("userid") long userid);
	
}