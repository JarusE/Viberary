package com.example.bookingservice.dto;

import lombok.Data;

/**
 * Represents the response for checking the availability of a specific book.
 * This class encapsulates the book's unique identifier and its availability status.
 *
 * The `bookId` field holds the unique identifier for the book being queried.
 * The `available` field indicates whether the book is currently available.
 */
@Data
public class BookAvailabilityResponse {
    private Long bookId;
    private boolean available;
}
