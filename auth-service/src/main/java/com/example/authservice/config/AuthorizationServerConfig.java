package com.example.authservice.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * Configuration class for setting up an OAuth2 Authorization Server.
 * This class is responsible for configuring beans and settings required
 * to implement an authorization server using Spring Security and OAuth2.
 *
 * The configuration includes:
 * - Security filter chains for the authorization server
 * - Client registration details with scopes and grant types
 * - RSA key generation for signing tokens
 * - JWK (JSON Web Key) source configuration
 * - Authorization server settings such as issuer URL
 *
 * An instance of {@link RegisteredClientRepository} is used to register
 * OAuth2 clients with specific scopes and authorization grant types.
 *
 * A pair of RSA keys is generated for use in signing the tokens issued by
 * the server, and the keys are made available via a JWK source bean.
 *
 * The authorization server settings, including the issuer URI, are configured
 * using the {@link AuthorizationServerSettings} bean.
 */
@Configuration
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient viberaryClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("viberary-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("read")
                .scope("write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(1))
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(viberaryClient);
    }

    @Bean
    public RSAKey rsaKey() {
        KeyPair kp = generateKeyPair();
        return new RSAKey.Builder((RSAPublicKey) kp.getPublic())
                .privateKey((RSAPrivateKey) kp.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            return gen.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://auth-service:9000")
                .build();
    }
}
