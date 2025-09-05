package com.example.bookingservice.dto;

/**
 * Represents a response containing user information.
 * This record encapsulates the user's unique identifier, email, name, and role,
 * typically used in scenarios where user data needs to be transferred between
 * services or layers in the system.
 *
 * Fields:
 * - id: The unique identifier of the user.
 * - email: The email address associated with the user.
 * - name: The name of the user.
 * - role: The role assigned to the user (e.g., USER, ADMIN).
 */
public record UserResponse(Long id, String email, String name, String role) {}
