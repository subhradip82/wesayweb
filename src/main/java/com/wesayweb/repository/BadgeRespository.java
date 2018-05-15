package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.wesayweb.model.Badges;

@NoRepositoryBean
public interface BadgeRespository<T extends Badges, ID extends Long> extends CrudRepository<T, ID> {

	@Override
	@Transactional(readOnly = true)
	@Query("select e from #{#Badges} e where e.badgeisactive = 1")
	List<T> findAll();
}