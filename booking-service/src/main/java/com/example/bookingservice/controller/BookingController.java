package com.example.bookingservice.controller;

import com.example.bookingservice.client.UserServiceClient;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.UserResponse;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The BookingController class handles HTTP requests related to booking operations.
 * It provides endpoints for users to create, retrieve, update, and manage their bookings.
 * Additionally, it includes administrative endpoints for managing user bookings.
 */
@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserServiceClient userServiceClient;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<Booking> book(@RequestBody Booking booking,
                                        @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        String token = jwt.getTokenValue();

        log.info("Jwt subject: {}", email);
        log.info("Jwt scope: {}", jwt.getClaimAsString("scope"));
        log.info("JSON received: {}", booking);

        UserResponse user = userServiceClient.getUserByEmail(email, token);

        if (user == null || user.id() == null) {
            throw new RuntimeException("User not found for email: " + email);
        }

        booking.setUserId(user.id());
        log.info("Reserve for the user: {}", user);

        return ResponseEntity.ok(bookingService.create(booking));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<Booking>> getUserBookings(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        String token = jwt.getTokenValue();
        UserResponse user = userServiceClient.getUserByEmail(email, token);
        return ResponseEntity.ok(bookingService.getUserBookings(user.id()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<BookingResponse>> getUserBookingsDto(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        String token = jwt.getTokenValue();
        UserResponse user = userServiceClient.getUserByEmail(email, token);
        return ResponseEntity.ok(bookingService.getUserBookingResponses(user.id()));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finish")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<Void> finishBooking(@PathVariable Long id,
                                              @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        String token = jwt.getTokenValue();
        UserResponse user = userServiceClient.getUserByEmail(email, token);
        bookingService.finish(id, user.id());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-log")
    public ResponseEntity<String> testLog(@AuthenticationPrincipal Jwt jwt) {
        log.info("BookingController is available. Jwt: {}", jwt);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/admin/user/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserBookingResponses(userId));
    }

    @PutMapping("/admin/{id}/cancel")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Void> cancelBookingByAdmin(@PathVariable Long id) {
        bookingService.cancel(id);
        return ResponseEntity.noContent().build();
    }

}
