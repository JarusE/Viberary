package com.example.bookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating and registering a {@link WebClient} bean.
 *
 * The {@link WebClient} is a non-blocking, reactive HTTP client provided by the Spring WebFlux framework
 * that facilitates interactions with RESTful web services and other HTTP-based APIs.
 *
 * This configuration simplifies the process of injecting a preconfigured {@code WebClient.Builder}
 * instance into other components of the application for performing reactive HTTP requests.
 *
 * Marked with {@link Configuration}, this class indicates that it is a source of Spring beans
 * and enables this configuration to be registered in the Spring application context.
 *
 * The {@code webClient} bean can be autowired into Spring components for usage.
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
