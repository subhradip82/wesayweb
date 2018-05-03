package com.wesayweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.SettingsCategory;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsCategory, Long> {

}