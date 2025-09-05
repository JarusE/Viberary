package com.example.authservice.dto;

/**
 * A record that encapsulates the registration request details.
 *
 * This record is used to represent the data necessary for user registration.
 * It contains the user's name, email, and password.
 *
 * Immutable and designed to be lightweight, it serves as a data transfer
 * object within registration-related operations.
 */
public record RegisterRequest(String name, String email, String password) {}
