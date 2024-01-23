package com.andpostman.routeprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "jms.queue.destination.send")
@Data
public class DestinationConfig {
    private final String error;
    private final String response;
}
