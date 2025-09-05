package com.example.authservice.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * Service for handling the creation of JSON Web Tokens (JWT) using RSA signing.
 * Provides functionality to generate tokens with specific roles and scopes.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final RSAKey rsaKey;

    public String generateToken(String subject, String role) {
        try {
            Instant now = Instant.now();

            String scope = switch (role.toUpperCase()) {
                case "ADMIN" -> "ROLE_USER ROLE_ADMIN";
                case "USER" -> "ROLE_USER";
                default -> "ROLE_USER";
            };

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .issuer("http://auth-service:9000")
                    .subject(subject)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plusSeconds(3600)))
                    .claim("scope", scope)
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(rsaKey.getKeyID())
                            .build(),
                    claims
            );

            signedJWT.sign(new RSASSASigner(rsaKey.toPrivateKey()));
            return signedJWT.serialize();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create token", e);
        }
    }

    public String generateInternalToken() {
        return generateToken("internal-service", "USER");
    }
}
