package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository is a data access layer interface responsible for managing
 * CRUD operations and custom queries on the User entity.
 *
 * This interface extends JpaRepository, which provides generic implementations for
 * standard database operations. It includes a custom method for retrieving users
 * based on their email address.
 *
 * Key Features:
 * - Inherits methods from JpaRepository such as findAll, findById, save, and deleteById.
 * - Contains a custom method, findByEmail, for retrieving a user by their email address.
 *
 * Methods:
 * - findByEmail(String email): Retrieves an Optional containing the User entity associated with the provided email.
 *
 * The UserRepository is typically used in service classes to perform operations
 * on the User database table and is automatically implemented by Spring Data JPA.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
