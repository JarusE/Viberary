package com.example.authservice.dto;

/**
 * Represents a user response object used for transferring user-related data.
 *
 * This record is utilized to encapsulate user details such as name, email,
 * hashed password, and role. It is primarily used as a response type for various
 * operations related to user management, such as retrieving or creating users.
 *
 * The role property indicates the user's role (e.g., USER or ADMIN) within the system.
 */
public record UserResponse(String name, String email, String password, String role) {}
