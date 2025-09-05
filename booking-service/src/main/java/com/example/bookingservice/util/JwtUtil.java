package com.example.bookingservice.util;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 * This class provides functionality to extract specific claims from JWT tokens.
 */
@Component
public class JwtUtil {

    public String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString("sub");
    }
}
