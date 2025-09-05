package com.example.userservice.service;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.model.User.Role;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The UserService class is a service layer component responsible for handling the business logic
 * related to user operations. It interacts with the UserRepository to manage the persistence
 * of user entities and provides various methods to retrieve, create, update, and delete user data.
 *
 * Key Responsibilities:
 * - Provides methods to retrieve users by ID, email, or as a full list.
 * - Handles user creation and updates with validation and mapping to DTOs.
 * - Registers new users with default roles.
 * - Deletes users by their unique ID.
 * - Maps User entities to UserResponse DTOs for data transfer purposes.
 *
 * Methods:
 * - getAllUsers(): Fetches all users from the repository and maps them to UserResponse objects.
 * - getUserById(Long id): Retrieves a user by their ID and maps the result to a UserResponse.
 * - getUserByEmail(String email): Retrieves a user by their email and maps the result to a UserResponse.
 * - createUser(UserRequest request): Creates a new user based on the provided UserRequest DTO, saves it, and returns a UserResponse.
 * - register(RegisterRequest request): Registers a new user with a default USER role and returns a UserResponse.
 * - updateUser(Long id, UserRequest request): Updates an existing user's information and returns a UserResponse.
 * - deleteUser(Long id): Deletes a user by their ID from the repository.
 * - mapToResponse(User user): A private helper method to map User entities to UserResponse DTOs.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public UserResponse createUser(UserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
        return mapToResponse(userRepository.save(user));
    }

    public UserResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        return mapToResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
