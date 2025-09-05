package com.example.userservice.controller;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController handles user-related operations and exposes REST API endpoints.
 * It provides functionality for user management, including authentication,
 * user registration, data access, and administrative operations.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/ping")
    public String ping() {
        return "user-service is working";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsersForAdmin() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
