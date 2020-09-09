package com.github.fwidder.timeFinder2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
public class TimeFinder2Application {

	public static void main(String[] args) {
		SpringApplication.run(TimeFinder2Application.class, args);
	}

}
