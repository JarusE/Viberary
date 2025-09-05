package com.example.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * A centralized exception handler for handling specific exceptions globally within
 * the Spring Boot application. This class ensures that custom exceptions are
 * translated into meaningful HTTP responses.
 *
 * This handler is marked with {@code @RestControllerAdvice} to provide a global
 * exception handling mechanism for all controllers in the application context.
 *
 * Methods in this class are mapped to specific custom exceptions to provide
 * tailored responses for clients that interact with the API.
 *
 * Exception Handled:
 * - {@link BookUnavailableException}: Represents scenarios where a requested book
 *   is unavailable for booking, e.g., not found or no available copies.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleUnavailable(BookUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
