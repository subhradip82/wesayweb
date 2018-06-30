package com.wesayweb.response.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wesayweb.model.Friends;

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
	public List<Friends> myfriends;
	
	@Getter
	@Setter
	public List<Friends> mySentfriendrequest;
	
	@Getter
	@Setter
	public List<Friends> myRecievedfriendrequest;
	
	

}