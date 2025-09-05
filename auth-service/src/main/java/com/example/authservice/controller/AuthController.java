package com.example.authservice.controller;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController provides REST endpoints for managing user authentication processes,
 * including user registration and login.
 *
 * This class leverages services like UserServiceClient for user management
 * and JwtService for generating JWT tokens. The endpoints are designed to handle
 * registration and login requests securely, encoding sensitive data and handling authentication.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            String encodedPassword = passwordEncoder.encode(request.password());
            RegisterRequest secureRequest = new RegisterRequest(
                    request.name(),
                    request.email(),
                    encodedPassword
            );

            UserResponse user = userServiceClient.createUser(secureRequest);

            String token = jwtService.generateToken(user.email(), user.role());

            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserResponse user = userServiceClient.getUserByEmail(request.email());

            String token = jwtService.generateToken(user.email(), user.role());

            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect data");
        }
    }
}
