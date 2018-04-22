package com.wesayweb.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.wesayweb.repository.UserRepository;

@SpringBootApplication()
@EnableWebMvc
@ComponentScan({ "com.wesayweb.controller", "com.wesayweb.conf" })
@EntityScan("com.wesayweb.model")
@EnableJpaRepositories("com.wesayweb.repository")
@EnableWebSecurity
public class WeSayWebApplication  extends WebSecurityConfigurerAdapter  implements WebMvcConfigurer  {

	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(WeSayWebApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
						"Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(true).maxAge(3600);

	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().authorizeRequests().antMatchers("/api/emailregistration").permitAll();
	}

}