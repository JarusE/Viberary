package com.example.bookingservice.client;

import com.example.bookingservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * The UserServiceClient class provides a client for interacting with an external User Service.
 * This class is designed to fetch user information by making HTTP requests to the User Service.
 *
 * The primary functionality of the class includes retrieving user details using their email address,
 * with a provided bearer token for authentication.
 *
 * This class uses the Spring WebClient to perform HTTP requests and handles potential errors
 * such as WebClientResponseException or unexpected exceptions.
 *
 * An example usage of this class can be observed in controllers or services where fetching user
 * details is required, typically as part of business logic dealing with bookings or other user-specific data.
 *
 * Dependencies:
 * - WebClient: Configured to perform HTTP requests to the User Service.
 *
 * Logging is used to log both successful operations and errors for better traceability.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {

    private final WebClient webClient;

    public UserResponse getUserByEmail(String email, String bearerToken) {
        try {
            return webClient
                    .get()
                    .uri("http://user-service:8081/users/by-email?email={email}", email)
                    .headers(headers -> headers.setBearerAuth(bearerToken))
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("User-service error: status={}, reason={}, body={}",
                    e.getStatusCode(), e.getMessage(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error when calling user-service", e);
            return null;
        }
    }
}
