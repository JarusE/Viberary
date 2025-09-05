package com.example.authservice.dto;

/**
 * Represents a user request object used for transferring user-related data.
 *
 * This class is primarily used to encapsulate user details such as name,
 * email, password, and role within the application. It facilitates the
 * construction and transfer of user-related information in various operations
 * such as user creation or management.
 *
 * The {@code Role} enum defines the possible roles a user can have, which
 * includes USER and ADMIN.
 */
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    public UserRequest() {}

    public UserRequest(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        USER, ADMIN
    }
}
