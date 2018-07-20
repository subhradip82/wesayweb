package com.wesayweb.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.wesayweb.repository.UserRepository;

@SpringBootApplication()
@EnableWebMvc
@ComponentScan({ "com.wesayweb.controller", "com.wesayweb.conf", "com.wesayweb" })
@EntityScan("com.wesayweb.model")
@EnableJpaRepositories("com.wesayweb.repository")

public class WeSayWebApplication implements WebMvcConfigurer {

	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		System.setProperty("server.tomcat.max-threads","200");
        System.setProperty("server.connection-timeout","120000");
		SpringApplication.run(WeSayWebApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "X-Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(true).maxAge(3600);

	}

}