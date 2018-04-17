package com.wesayweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wesayweb.model.Traits;
import com.wesayweb.model.User;

@Repository
public interface TraitsCustomRepository {

	public List<Traits> getActiveTraits();

}