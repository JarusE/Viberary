package com.example.bookingservice.repository;

import com.example.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing and accessing {@link Booking} entities from the database.
 *
 * This interface extends {@link JpaRepository} to provide CRUD and query capabilities for
 * {@link Booking}. Additionally, it defines custom query methods for specific booking-related
 * requirements, such as finding bookings by user ID and identifying conflicts in booking reservations.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.bookId = :bookId " +
            "AND b.startDate <= :endDate AND b.endDate >= :startDate " +
            "AND b.status IN ('PENDING', 'ACTIVE')")
    boolean hasConflict(@Param("bookId") Long bookId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(b) FROM Booking b " +
            "WHERE b.bookId = :bookId " +
            "AND b.startDate <= :endDate AND b.endDate >= :startDate " +
            "AND b.status IN ('PENDING', 'ACTIVE')")
    int countConflictingBookings(@Param("bookId") Long bookId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

}
