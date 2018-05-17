package com.wesayweb.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Badges;

@Repository
public interface BadgeCustomRespository {

	@Query(value = " WITH FRIENDS_COUNT AS  " + "(  "
			+ "    SELECT COUNT(*) AS total , 1 AS USERID FROM  friend_list  where USERID = :sourceuserid " + ") "
			+ ", GIVEN_BADGE_COUNT AS  " + "(  "
			+ "    SELECT COUNT(*) AS TOTAL, 1 AS USERID from user_badge  where badgegivenby = :sourceuserid " + ")  "
			+ " "
			+ "SELECT   (A.TOTAL - CASE WHEN B.TOTAL IS NULL THEN 0 ELSE B.TOTAL END)/5 AS TOTAL_AVAILABLE_BADGE  "
			+ "FROM FRIENDS_COUNT A FULL OUTER JOIN GIVEN_BADGE_COUNT B ON A.USERID = B.USERID ", nativeQuery = true, name = "getNumberOfEligibleBadges")

	public List<BigInteger> getNumberOfEligibleBadges(@Param("sourceuserid") long sourceuserid);

	public List<Badges> getAvailableBadges();
}