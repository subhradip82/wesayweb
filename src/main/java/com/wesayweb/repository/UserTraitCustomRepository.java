package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.UserTrait;
import com.wesayweb.request.model.UserTraitRequest;
import com.wesayweb.response.model.TraitListResponse;
import com.wesayweb.response.model.UserTraitsResponsePojo;

@Repository
public interface UserTraitCustomRepository {
 
	@Query(value = "WITH TRAIT AS (  " + 
			" SELECT A.traitname, 'predefined' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, B.typeofvote, B.ishidden, B.id FROM trait_master A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  "
			+ "  AND  B.isactive = 1 " + 
			" UNION ALL  " + 
			" SELECT  A.traitname, 'custom' AS traittype, A.traitdescripion , A.traiticonpath , A.traituniqueid, "
			+ " B.typeofvote, B.ishidden , B.id FROM "
			+ " custom_traits A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  AND B.ishidden = 0 "
			+ "  AND  B.isactive = 1   " + 
			" ) " + 
			"select traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,traittype, " + 
			"	   SUM(CASE WHEN typeofvote = 0 THEN 1 ELSE 0 END) AS positive, " + 
			"	   SUM(CASE WHEN typeofvote = 1 THEN 1 ELSE 0 END) AS   negetive , " + 
			"	   SUM(CASE WHEN typeofvote = 2 THEN 1 ELSE 0 END) AS nutral  ,ishidden, id  " + 
			"	   from TRAIT GROUP BY  " + 
			"	   traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,"
			+ "    traittype  , ishidden, id " + 
			"       ORDER BY traitname", nativeQuery = true, name = "getmytraits")

	List<Object[]> getMyTraits(@Param("traitgivenfor") long traitgivenfor);

	@Query(value = "WITH TRAIT AS (  " + 
			" SELECT A.traitname, 'predefined' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, B.typeofvote, B.ishidden, CASE WHEN B.traitgivenby = :traitgivenby THEN 1 ELSE 0 END AS istraitigave, B.id FROM trait_master A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  "
			+ "  AND  B.isactive = 1  AND B.iswaitingforapproval = 0 " + 
			" UNION ALL  " + 
			" SELECT  A.traitname, 'custom' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, "
			+ " B.typeofvote , B.ishidden, CASE WHEN B.traitgivenby = :traitgivenby THEN 1 ELSE 0 END AS istraitigave,  B.id FROM "
			+ " custom_traits A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  AND B.ishidden = 0 "
			+ "  AND  B.isactive = 1  AND B.iswaitingforapproval = 0    " + 
			" ) , total_trait AS ( " + 
			"select traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,"
			+ "		traittype, " + 
			"	   SUM(CASE WHEN typeofvote = 0 THEN 1 ELSE 0 END) AS positive, " + 
			"	   SUM(CASE WHEN typeofvote = 1 THEN 1 ELSE 0 END) AS   negetive , " + 
			"	   SUM(CASE WHEN typeofvote = 2 THEN 1 ELSE 0 END) AS nutral ,ishidden , id   " + 
			"	   from TRAIT GROUP BY  " + 
			"	   traituniqueid, " + 
			"	   traitname , " + 
			"	   traiticonpath,"
			+ "    traittype , ishidden , id " + 
			"        ) "
			+ " SELECT a.traituniqueid,a.traitname,a.traiticonpath,a.traittype,a.positive,a.negetive,"
			+ " a.nutral, a.ishidden ,b.istraitigave , "
			+ "CASE WHEN b.typeofvote = 0 THEN 'y' ELSE 'n' END  as my_positive_vote, "
			+ "CASE WHEN b.typeofvote = 1 THEN 'y' ELSE 'n' END  as my_negetive_vote, "
			+ "CASE WHEN b.typeofvote = 2 THEN 'y' ELSE 'n' END  as my_neutral_vote, id FROM  total_trait "
			+ "a LEFT JOIN TRAIT B ON "
			+ "CASE WHEN b.istraitigave = 1 THEN  a.traituniqueid = b.traituniqueid  else 1= 2 end"
			+ "", nativeQuery = true, name = "getMyFriendsTraits")

	List<Object[]> getMyFriendsTraits(@Param("traitgivenfor") long traitgivenfor , @Param("traitgivenby") long traitgivenby);
	
	void saveUserTraits(UserTrait userTraitObj);
	void updateUserTrait(UserTrait userTraitObj);
	void deleteTrait(UserTrait userTraitObj);
	void hideTrait(UserTrait userTraitObj);
	void approveCustomTrait(UserTrait userTraitObj);
	void unHideTrait(UserTrait userTraitObj);
	List<UserTrait>  listOfUnreadTrait(Long userid); 
	
	@Query(value = "WITH TRAIT AS (  " + 
			" SELECT A.traitname, B.creationdate, B.traitgivenby, 'predefined' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, B.typeofvote, B.ishidden FROM trait_master A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor  "
			+ "  AND  B.iswaitingforapproval = 1  and B.isactive = 1" + 
			" UNION ALL  " + 
			" SELECT  A.traitname, B.creationdate, B.traitgivenby, 'custom' AS traittype, A.traitdescripion , A.traiticonpath , "
			+ " A.traituniqueid, "
			+ " B.typeofvote ,  B.ishidden FROM "
			+ " custom_traits A JOIN user_trait B " + 
			" ON A.traituniqueid = B.traituniqueid WHERE B.traitgivenfor = :traitgivenfor "
			+ " AND  B.iswaitingforapproval = 1  and B.isactive = 1  " + 
			" ) " + 
			"select a.traituniqueid, CASE WHEN a.ishidden = 1 then '' else  b.fullname  end as fullname ," + 
			"	   a.traitname , " + 
			"	   CASE WHEN a.traiticonpath IS NULL THEN '' ELSE a.traiticonpath END AS traiticonpath," 
			+ "	   a.traittype, " + 
			"	   a.typeofvote ,a.creationdate  " + 
			"	   from TRAIT a join user_master B on a.traitgivenby = b.id   " + 
			"       ORDER BY traitname", nativeQuery = true, name = "traitsWaitingForApproval")
	List<Object[]> traitsWaitingForApproval(@Param("traitgivenfor") Long traitgivenfor);

	void deleteATrait(Long traitId);
	void hideATrait(Long traitId);
	void unHideATrait(Long traitId);
	void hideUnHideTraitCount(UserTraitRequest userTraitRequest);
	public List<UserTrait>  getTraitDetails(String userTraitIdid);
}