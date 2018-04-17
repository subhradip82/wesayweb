package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Traits;

@Repository
public interface TraitRepository extends JpaRepository<Traits, Long> , TraitsCustomRepository {

}