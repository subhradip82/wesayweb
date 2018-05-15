package com.wesayweb.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wesayweb.service.EmailService;

@Configuration
public class EmailConfiguration {

	@Bean
	public EmailService getEmailService() {
		return new EmailService();
	}
	
}