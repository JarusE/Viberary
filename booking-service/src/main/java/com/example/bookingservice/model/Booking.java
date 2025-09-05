package com.example.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    /**
     * Unique identifier for the Booking entity.
     * It is automatically generated using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Represents the unique identifier of a user associated with a booking.
     * This variable is used to link a booking to a specific user in the system.
     */
    private Long userId;

    /**
     *
     */
    private Long bookId;

    /**
     * Represents the start date of the booking.
     * This field is formatted as "yyyy-MM-dd" for consistent date representation.
     * It is used to determine the beginning of the booking period and is a key component
     * in verifying date conflicts and overlaps with other bookings.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * Represents the end date of a booking.
     * This field is serialized and deserialized in the format 'yyyy-MM-dd'.
     * It indicates the last date of the booking period.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * Represents the current status of the booking.
     * This is an enumerated type that can hold values such as PENDING, CONFIRMED, FINISHED, ACTIVE, or CANCELLED.
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        FINISHED,
        ACTIVE, CANCELLED
    }
}
