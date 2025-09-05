package com.example.bookingservice.exception;

/**
 * This exception is thrown when a user attempts to book a book
 * that they have already booked and the booking is either in a
 * PENDING or ACTIVE state.
 *
 * It indicates a conflicting booking operation for the same book
 * by the same user.
 */
public class BookAlreadyBookedException extends RuntimeException {
    public BookAlreadyBookedException(String message) {
        super(message);
    }
}
