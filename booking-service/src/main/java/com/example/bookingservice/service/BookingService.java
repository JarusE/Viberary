package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.exception.BookAlreadyBookedException;
import com.example.bookingservice.exception.BookUnavailableException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookServiceClient bookServiceClient;

    public Booking create(Booking booking) {
        boolean alreadyBooked = bookingRepository.findByUserId(booking.getUserId()).stream()
                .anyMatch(b -> b.getBookId().equals(booking.getBookId())
                        && (b.getStatus() == Booking.BookingStatus.PENDING || b.getStatus() == Booking.BookingStatus.ACTIVE));

        if (alreadyBooked) {
            throw new BookAlreadyBookedException("You have already booked this book.");
        }

        int activeBookings = bookingRepository.countConflictingBookings(
                booking.getBookId(),
                booking.getStartDate(),
                booking.getEndDate()
        );

        var book = bookServiceClient.getBookById(booking.getBookId());
        if (book == null || book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Book not found or no copies available");
        }

        if (activeBookings >= book.getAvailableCopies()) {
            throw new BookUnavailableException("There are no copies available for the selected period.");
        }

        booking.setStatus(Booking.BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }


    public List<BookingResponse> getUserBookingResponses(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(booking -> BookingResponse.builder()
                        .id(booking.getId())
                        .bookId(booking.getBookId())
                        .startDate(booking.getStartDate())
                        .endDate(booking.getEndDate())
                        .status(booking.getStatus().name())
                        .build())
                .toList();
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public void finish(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("No booking found"));

        if (!booking.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: This is not your booking");
        }

        if (booking.getStatus() != Booking.BookingStatus.CANCELLED) {
            booking.setStatus(Booking.BookingStatus.FINISHED);
            bookingRepository.save(booking);
        }
    }
}
