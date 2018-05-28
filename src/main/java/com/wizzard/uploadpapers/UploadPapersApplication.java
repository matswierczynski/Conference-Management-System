package com.wizzard.uploadpapers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UploadPapersApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UploadPapersApplication.class);
	}

	public static void main(java.lang.String[] args) {
		SpringApplication.run(UploadPapersApplication.class, args);
	}
	
}
