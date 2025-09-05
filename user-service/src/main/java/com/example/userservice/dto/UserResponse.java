package com.example.userservice.dto;

import com.example.userservice.model.User.Role;
import lombok.*;

/**
 * The UserResponse class is a Data Transfer Object (DTO) representing the response
 * structure for user data being sent to the client.
 *
 * This class encapsulates user-related data such as the user's ID, name, email, password,
 * and role. It is primarily used in service and controller layers to format and present
 * the user information in response to client requests.
 *
 * Fields:
 * - id: Unique identifier for the user.
 * - name: The name of the user.
 * - email: The email address of the user.
 * - password: The password associated with the user's account.
 * - role: The role assigned to the user, which determines their permissions and access level.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
