package com.wesayweb.response.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wesayweb.model.ContactList;
import com.wesayweb.model.Friends;
import com.wesayweb.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyFriendsZoneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	public List<User> myfriends;
	
	@Getter
	@Setter
	public List<User> mySentfriendrequest;
	
	@Getter
	@Setter
	public List<User> myRecievedfriendrequest;
	
	@Getter
	@Setter
	public List<ContactList> myContactList;
	

}