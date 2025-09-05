package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class to define Cross-Origin Resource Sharing (CORS) settings
 * for the application.
 *
 * This class configures CORS policies to manage and restrict requests from
 * external origins to the application resources. It defines:
 * - Allowed origins: Specifies which domains can make requests to this application.
 * - Allowed methods: Lists HTTP methods permitted for cross-origin requests.
 * - Allowed headers: Specifies which HTTP headers can be included in the request.
 * - Exposed headers: Determines which response headers are exposed to the client.
 * - Credentials: Allows or disallows the inclusion of user credentials (cookies,
 *   authorization headers, etc.) in cross-origin requests.
 *
 * The configuration is applied globally to all paths in the application by registering
 * the CORS settings with a UrlBasedCorsConfigurationSource.
 *
 * A CorsWebFilter bean is created to enforce the defined CORS policy on web requests.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
