package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.UserTrait;
import com.wesayweb.response.model.TraitsResponsePojo;

@Repository
public interface UserTraitCustomRepository   {

	public List<UserTrait> getMyTraits(long givenby, long tragetUerId);
	
	@Query(value = "WITH TRAIT AS " + 
			"(" + 
			"select id as trait_id, traitname , traitdescripion,traiticonpath  from   trait_master " + 
			")" + 
			",USER_DATA AS ( SELECT traitid,typeofvote,isannonymous FROM user_trait"
			+ " WHERE  targetuserid = :targetuserid) , CONSOLIDATED_DATA AS (" + 
			"select A.trait_id, A.traitname , " + 
			"    CASE WHEN B.typeofvote = 0 THEN 1 ELSE 0 END AS positive, " + 
			"    CASE WHEN B.typeofvote = 1 THEN 1 ELSE 0 END AS   negetive ," + 
			"    CASE WHEN B.typeofvote = 2 THEN 1 ELSE 0 END AS nutral," + 
			"    A.traitdescripion," + 
			"    A.traiticonpath," + 
			"    B.isannonymous " + 
			"    from TRAIT A LEFT OUTER JOIN  USER_DATA  B " + 
			"	ON A.trait_id = B.traitid " + 
			"  ) " + 
			"SELECT trait_id, traitname, traitdescripion,traiticonpath, SUM(positive) AS positive ,SUM(negetive) AS negetive ,SUM(nutral) AS nutral ,SUM(isannonymous) AS isannonymous   from CONSOLIDATED_DATA " + 
			" GROUP BY  trait_id, traitname, traitdescripion,traiticonpath " + 
			" ORDER BY traitname", nativeQuery = true, name="getmytraits" )
    
	List<Object[]> getMyTraits(@Param("targetuserid") long targetuserid);
	 
	
}