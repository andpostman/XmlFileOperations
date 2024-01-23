package com.andpostman.routeprocessor;

import com.andpostman.routeprocessor.config.DestinationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(DestinationConfig.class)
@EnableScheduling
public class RouteProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteProcessorApplication.class, args);
	}

}
