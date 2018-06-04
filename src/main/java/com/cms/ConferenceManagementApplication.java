package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//Starting class
@SpringBootApplication
public class ConferenceManagementApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ConferenceManagementApplication.class);
    }

    public static void main(java.lang.String[] args) {
        SpringApplication.run(ConferenceManagementApplication.class, args);
    }

}
