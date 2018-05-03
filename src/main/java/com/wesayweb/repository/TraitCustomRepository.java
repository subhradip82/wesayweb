package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.CustomTraits;
import com.wesayweb.model.Traits;

@Repository
public interface TraitCustomRepository {

	public List<Traits> getActiveTraits(int traittype, int limit);

	
	@Query(value = "select  a.traituniqueid from user_trait a \r\n" + 
			"JOIN  custom_traits b ON a.traituniqueid = b.traituniqueid "
			+ " WHERE a.traitgivenby = :traitgivenby and a.traitgivenfor = :traitgivenfor and "
			+ " lower(trim(b.traitname))= :traitname "
			+ "  UNION ALL "
			+ "select  a.traituniqueid from user_trait a \r\n" + 
			"JOIN  trait_master b ON a.traituniqueid = b.traituniqueid "
			+ " WHERE a.traitgivenby = :traitgivenby and a.traitgivenfor = :traitgivenfor and "
			+ " lower(trim(b.traitname))= :traitname "
			+ "", nativeQuery = true, name = "traitAlreadyExists")

	public List<String> traitAlreadyExists(@Param("traitname") String traitname,
						@Param("traitgivenby") long traitgivenby,
						@Param("traitgivenfor") long traitgivenfor);

	
	@Query(value = "select  a.traituniqueid from user_trait a \r\n" + 
			"JOIN  custom_traits b ON a.traituniqueid = b.traituniqueid "
			+ " WHERE a.traitgivenby = :traitgivenby "
			+ " and a.traitgivenfor = :traitgivenfor and lower(trim(b.traitname))= :traitname ", nativeQuery = true, 
			name = "customTraitAlreadyExists")

	public List<String> customTraitAlreadyExists(@Param("traitname") String traitname,
						@Param("traitgivenby") long traitgivenby,
						@Param("traitgivenfor") long traitgivenfor);

	
	@Query(value = "select  * from trait_master WHERE  lower(trim(traitname))= :traitname ", nativeQuery = true, 
			name = "definedTraitAlreadyExists")

	public List<Traits> definedTraitAlreadyExists(@Param("traitname") String traitname);
	public CustomTraits saveCustomTrait(CustomTraits customTrait);
	boolean removeTrait(Traits traitObj);
	void removealltraits();
}