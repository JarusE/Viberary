package com.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 *
 * This class provides a {@link PasswordEncoder} bean to handle password encoding
 * and validation in the application. The configured encoder uses
 * {@link BCryptPasswordEncoder}, a strong hashing function that incorporates
 * salting and an adaptive algorithm to protect against brute force attacks.
 *
 * Bean provided:
 * - {@link PasswordEncoder}: Encodes and matches passwords using BCrypt.
 */
@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
