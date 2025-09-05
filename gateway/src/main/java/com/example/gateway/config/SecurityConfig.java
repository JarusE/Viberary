package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.Customizer;

/**
 * Configuration class to define security settings for the application.
 *
 * This class configures the security for a WebFlux-based application, setting up
 * authentication, authorization, and security features for incoming HTTP requests.
 *
 * Key configurations:
 * - CORS: Enables Cross-Origin Resource Sharing with default settings to allow
 *   interaction with APIs from different origins.
 * - CSRF: Disables Cross-Site Request Forgery protection.
 * - Authorization: Provides custom access rules, permitting all OPTIONS requests
 *   and requests to paths under "/api/books/**", while requiring authentication
 *   for all other requests.
 * - OAuth2 Resource Server: Configures the application to validate JWT tokens for
 *   secured access to resources.
 *
 * A SecurityWebFilterChain bean is created to enforce the defined security policies
 * on web requests.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/api/books/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }
}
