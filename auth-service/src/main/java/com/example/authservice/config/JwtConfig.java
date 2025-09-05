package com.example.authservice.config;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.source.JWKSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

/**
 * Configuration class for JWT encoder and decoder beans.
 *
 * This class is responsible for setting up and providing the necessary
 * components for processing JSON Web Tokens (JWTs) in the application. The
 * configured components enable encoding and decoding of JWTs using a
 * provided JWK (JSON Web Key) source.
 *
 * Beans provided:
 * - {@link JwtEncoder}: Encodes JWTs using the specified JWK source.
 * - {@link JwtDecoder}: Decodes and validates JWTs using the specified JWK source.
 */
@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
