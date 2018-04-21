package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.Traits;

@Repository
public interface TraitCustomRepository {

	public List<Traits> getActiveTraits();

	public boolean traitAlreadyExists(String traitName);
	public boolean ifTraitIsUpdatable(Traits trailObj);
	public boolean updateTrait(Traits traitObj);
	public boolean deleteTrait(Long traitid );
	boolean removeTrait(Traits traitObj);
}