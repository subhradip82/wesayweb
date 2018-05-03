package com.wesayweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesayweb.model.SettingsCategory;

@Repository
public interface SettingsCustomRepository   {

	public List<SettingsCategory> getListOfSettingsCategory();
}