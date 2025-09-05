package com.example.bookingservice.exception;

/**
 * This exception is thrown when a requested book is either not found
 * or does not have any available copies for booking.
 *
 * It is used to signal that the requested book cannot be processed
 * for booking due to unavailability.
 */
public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String message) {
        super(message);
    }
}
