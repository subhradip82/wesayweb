package com.wesayweb.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wesayweb.repository.UserRepository;


@SpringBootApplication()
@ComponentScan({"com.wesayweb.controller"})
@EntityScan("com.wesayweb.model")
@EnableJpaRepositories("com.wesayweb.repository")
public class WeSayWebApplication {

	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(WeSayWebApplication.class, args);
	} 

}