package com.example.authservice.dto;

/**
 * Represents a data transfer object for login requests.
 *
 * This record is used for encapsulating login-related information such as
 * the user's email and password. It is typically utilized when a client sends
 * login credentials to authenticate a user in the system.
 */
public record LoginRequest(String email, String password) {}
