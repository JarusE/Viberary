package com.example.authservice.client;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserRequest;
import com.example.authservice.dto.UserRequest.Role;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    /**
     * A RestTemplate instance used for making HTTP requests to external services.
     * It facilitates communication with the User Service by sending and receiving data.
     * This object is final, ensuring that the RestTemplate instance is immutable and cannot be reassigned.
     */
    private final RestTemplate restTemplate;
    /**
     * Handles operations related to JSON Web Token (JWT) creation and management.
     * Utilized to generate authentication tokens for interacting with other services
     * and securing requests.
     */
    private final JwtService jwtService;

    /**
     * The URL of the user service used for making HTTP requests to interact
     * with the user-related operations of an external service.
     * This value is injected from the application's configuration properties
     * using the {@code user.service.url} property.
     */
    @Value("${user.service.url}")
    private String userServiceUrl;

    /**
     * Creates a new user based on the provided registration request.
     *
     * @param request the registration details including name, email, and password
     * @return a response object containing the created user's details
     */
    public UserResponse createUser(RegisterRequest request) {
        String token = jwtService.generateInternalToken();

        UserRequest userRequest = new UserRequest(
                request.name(),
                request.email(),
                request.password(),
                Role.USER
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<UserResponse> response = restTemplate.postForEntity(
                userServiceUrl + "/users",
                entity,
                UserResponse.class
        );

        return response.getBody();
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to be retrieved
     * @return a UserResponse object containing the user details, or null if the user is not found
     */
    public UserResponse getUserByEmail(String email) {
        String token = jwtService.generateInternalToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                userServiceUrl + "/users/by-email?email=" + email,
                HttpMethod.GET,
                entity,
                UserResponse.class
        );
        return response.getBody();
    }

}
