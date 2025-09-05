package com.example.bookingservice.exception;

public class BookAlreadyBookedException extends RuntimeException {
    public BookAlreadyBookedException(String message) {
        super(message);
    }
}
