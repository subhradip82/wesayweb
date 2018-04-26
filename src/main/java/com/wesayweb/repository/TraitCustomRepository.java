package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;

@Repository
public interface TraitCustomRepository {

	public List<Traits> getActiveTraits(int traittype);

	/*@Query(value = "WITH CUSTOM_DATA_SET AS ( " + 
			"select  b.traitname from wesayweb.user_trait a JOIN  wesayweb.custom_traits B ON \r\n" + 
			"a.traitid = B.id WHERE a.traitgivenby = :traitgivenby and a.traitgivenfor = :traitgivenfor and lower(trim(b.traitname)) = :" + 
			"UNION ALL\r\n" + 
			"SELECT traitname from wesayweb.trait_master \r\n" + 
			" ) \r\n" + 
			" SELECT * FROM CUSTOM_DATA_SET", nativeQuery = true, name = "traitAlreadyExists")

*/	 
	public boolean traitAlreadyExists(@Param("traitname") String traitname, 
									  @Param("traitgivenby") long traitgivenby, 
									  @Param("traitgivenfor") long traitgivenfor);
	public boolean ifTraitIsUpdatable(Traits trailObj);
	public boolean updateTrait(Traits traitObj);
	public boolean deleteTrait(Long traitid );
	public boolean saveCustomTrait(CustomTraits customTrait);
	boolean removeTrait(Traits traitObj);
}