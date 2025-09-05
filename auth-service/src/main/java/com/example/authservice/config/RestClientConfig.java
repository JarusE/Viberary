package com.example.authservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for configuring RestTemplateBuilder bean.
 *
 * This class is responsible for providing a {@link RestTemplateBuilder}
 * bean that can serve as a base for customizing {@link RestTemplate} instances
 * within the application. The builder allows easy modification and setup
 * of configurations such as default headers, timeouts, and error handling
 * for REST client requests.
 *
 * Bean provided:
 * - {@link RestTemplateBuilder}: A builder for {@link RestTemplate} instances.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
