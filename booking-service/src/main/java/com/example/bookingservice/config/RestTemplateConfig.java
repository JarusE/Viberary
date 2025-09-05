package com.example.bookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating and registering a {@link RestTemplate} bean.
 *
 * The {@link RestTemplate} is a synchronous HTTP client that can be used to perform
 * RESTful service invocations with various HTTP methods (e.g., GET, POST, PUT, DELETE).
 *
 * This configuration simplifies the process of injecting a preconfigured {@code RestTemplate}
 * instance into other components of the application.
 *
 * Marked with {@link Configuration}, this class indicates that it contains one or more
 * Spring beans that should be processed and instantiated by the Spring application context.
 *
 * The {@code restTemplate} bean can be autowired into other Spring components for usage.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
