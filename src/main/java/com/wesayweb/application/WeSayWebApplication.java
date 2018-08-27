package com.wesayweb.application;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.wesayweb.repository.UserRepository;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableWebMvc
@ComponentScan({ "com.wesayweb.controller", "com.wesayweb.conf", "com.wesayweb" })
@EntityScan("com.wesayweb.model")
@EnableJpaRepositories("com.wesayweb.repository")
@EnableSwagger2

public class WeSayWebApplication implements WebMvcConfigurer {

	@Autowired
	UserRepository repository;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	
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

	private ApiInfo getApiInfo() {
		Contact contact = new Contact("WeSay, Inc.", "https://www.wesay.com/", "info@wesay.com");
		return new ApiInfoBuilder().title("wesay Api").description("wesay Api Definition")
				.version("1.0.0").license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.contact(contact).build();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.wesayweb.controller"))
				.paths(PathSelectors.any()).build()
				 .securitySchemes(Collections.singletonList(apiKey()));

	}

	private ApiKey apiKey() {

		 return new ApiKey("Api key authorization", "x-authorization", "header");
	}
}