package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The RegisterRequest class is used to encapsulate data for user registration requests.
 * It contains fields for capturing basic user details such as name, email, and password.
 * This class is commonly utilized as a Data Transfer Object (DTO) in user registration processes.
 *
 * Fields:
 * - name: Represents the name of the user to be registered.
 * - email: Represents the email address of the user to be registered.
 * - password: Represents the password for the account of the user being registered.
 *
 * An instance of this class is typically passed to service methods that handle
 * user registration functionality.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
