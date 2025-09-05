package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a user entity in the system.
 *
 * This class is mapped to a database table and is used to store and retrieve user-related information.
 * It contains fields for the user's ID, name, email, password, and role. The class supports builder
 * methods for easy object creation and is annotated with commonly used Lombok annotations for
 * reducing boilerplate code.
 *
 * Key Features:
 * - Each user has a unique identifier (`id`), which is auto-generated.
 * - Supports multiple roles (`USER`, `ADMIN`) using an enumerated type (`Role`).
 * - Lombok annotations provide getter and setter methods, a no-args constructor,
 *   an all-args constructor, and a builder for object instantiation.
 * - Annotated with JPA annotations to enable persistence with a relational database.
 *
 * Enum:
 * - Role: Defines roles assigned to users, such as USER and ADMIN.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
