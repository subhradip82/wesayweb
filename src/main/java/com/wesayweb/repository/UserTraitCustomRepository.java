package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.UserTrait;

@Repository
public interface UserTraitCustomRepository {
 
	@Query(value = "WITH TRAIT AS (  " + 
			" SELECT A.traitname, 'predefined' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, B.typeofvote FROM trait_master A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  "
			+ "  AND  B.isactive = 1 " + 
			" UNION ALL  " + 
			" SELECT  A.traitname, 'custom' AS traittype, A.traitdescripion , A.traiticonpath , A.traituniqueid, "
			+ " B.typeofvote FROM "
			+ " custom_traits A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  AND B.ishidden = 0 "
			+ "  AND  B.isactive = 1   " + 
			" ) " + 
			"select traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,traittype, " + 
			"	   SUM(CASE WHEN typeofvote = 0 THEN 1 ELSE 0 END) AS positive, " + 
			"	   SUM(CASE WHEN typeofvote = 1 THEN 1 ELSE 0 END) AS   negetive , " + 
			"	   SUM(CASE WHEN typeofvote = 2 THEN 1 ELSE 0 END) AS nutral   " + 
			"	   from TRAIT GROUP BY  " + 
			"	   traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,"
			+ "    traittype   " + 
			"       ORDER BY traitname", nativeQuery = true, name = "getmytraits")

	List<Object[]> getMyTraits(@Param("traitgivenfor") long traitgivenfor);

	@Query(value = "WITH TRAIT AS (  " + 
			" SELECT A.traitname, 'predefined' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, B.typeofvote FROM trait_master A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  "
			+ "  AND  B.isactive = 1  AND B.ishidden = 0  " + 
			" UNION ALL  " + 
			" SELECT  A.traitname, 'custom' AS traittype, A.traitdescripion , A.traiticonpath , A.traituniqueid, "
			+ " B.typeofvote FROM "
			+ " custom_traits A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  AND B.ishidden = 0 "
			+ "  AND  B.isactive = 1  AND B.ishidden = 0    " + 
			" ) " + 
			"select traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,traittype, " + 
			"	   SUM(CASE WHEN typeofvote = 0 THEN 1 ELSE 0 END) AS positive, " + 
			"	   SUM(CASE WHEN typeofvote = 1 THEN 1 ELSE 0 END) AS   negetive , " + 
			"	   SUM(CASE WHEN typeofvote = 2 THEN 1 ELSE 0 END) AS nutral   " + 
			"	   from TRAIT GROUP BY  " + 
			"	   traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,"
			+ "    traittype   " + 
			"       ORDER BY traitname", nativeQuery = true, name = "getMyFriendsTraits")

	List<Object[]> getMyFriendsTraits(@Param("traitgivenfor") long traitgivenfor);
	
	void saveUserTraits(UserTrait userTraitObj);
	void updateUserTrait(UserTrait userTraitObj);
	
	void deleteTrait(UserTrait userTraitObj);
	void hideTrait(UserTrait userTraitObj);
	void approveCustomTrait(UserTrait userTraitObj);
	void unHideTrait(UserTrait userTraitObj);
	List<UserTrait>  listOfUnreadTrait(Long userid);
}