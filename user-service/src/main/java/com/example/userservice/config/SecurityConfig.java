package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class responsible for setting up application security policies
 * and behavior using Spring Security.
 *
 * This configuration class enables method-level security and defines a bean for the
 * security filter chain to handle HTTP security rules and authorization configurations.
 *
 * The following security behaviors are configured:
 * - Specific HTTP request matches are configured to allow or restrict access based on roles or authorities.
 * - OAuth2 resource server is configured to support JWT-based authentication.
 * - Disables CSRF protection and enables default CORS settings.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/by-email").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("SCOPE_ROLE_USER", "SCOPE_read", "SCOPE_write")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults());

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName("sub");
        return converter;
    }
}
