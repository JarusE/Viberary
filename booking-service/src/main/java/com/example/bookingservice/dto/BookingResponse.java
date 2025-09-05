package com.example.bookingservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a response for a booking, providing details about the booking and associated book.
 * This class includes information such as the booking id, book details, booking duration, and current status.
 *
 * The class is annotated with Lombok annotations:
 * - {@code @Data} to generate getter, setter, equals, hashCode, and toString methods.
 * - {@code @Builder} to provide a builder pattern implementation for object creation.
 *
 * Fields:
 * - {@code id}: Unique identifier for the booking.
 * - {@code bookId}: Identifier of the book related to the booking.
 * - {@code bookTitle}: Title of the booked item.
 * - {@code startDate}: Starting date of the booking period.
 * - {@code endDate}: Ending date of the booking period.
 * - {@code status}: Current status of the booking (e.g., confirmed, canceled, pending).
 */
@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
