package com.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for defining application-wide beans and settings.
 *
 * This class is responsible for configuring and providing the necessary
 * components used across the application. Specifically, it supplies a bean
 * for {@link RestTemplate}, which facilitates RESTful HTTP communication
 * between services in the application.
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
