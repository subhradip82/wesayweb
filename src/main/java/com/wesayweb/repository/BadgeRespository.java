package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.Badges;

@Repository
public interface BadgeRespository  extends JpaRepository<Badges, Long>, BadgeCustomRespository    {

}