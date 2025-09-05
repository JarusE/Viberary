package com.example.userservice.dto;

import com.example.userservice.model.User.Role;
import lombok.*;

/**
 * The UserRequest class is a Data Transfer Object (DTO) used for encapsulating
 * user-related data required for creating or updating a user.
 *
 * It is commonly utilized in service and controller layers to handle incoming
 * user data in a structured form. This class provides fields for capturing
 * user information such as name, email, password, and role.
 *
 * Fields:
 * - name: The name of the user.
 * - email: The email address of the user.
 * - password: The password associated with the user's account.
 * - role: The role of the user, such as USER or ADMIN, which determines their permissions.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
