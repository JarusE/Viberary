package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookAvailabilityResponse;
import com.example.bookingservice.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

/**
 * A client service for interacting with the Book Service API.
 * This service provides methods to check the availability of books and
 * retrieve detailed information about specific books through HTTP requests.
 *
 * Dependencies:
 * - {@link WebClient}: Used for making non-blocking HTTP requests to
 *   the Book Service API.
 * - Configured Base URL: The base URL for the Book Service API is
 *   specified via the `services.book-service.url` property. If not configured,
 *   it defaults to "http://book-service:8082".
 *
 * Key Responsibilities:
 * - Verify whether a specific book is available for a given date range.
 * - Fetch information about a book by its unique identifier.
 */
@Service
@RequiredArgsConstructor
public class BookServiceClient {

    private final WebClient webClient;

    @Value("${services.book-service.url:http://book-service:8082}")
    private String bookServiceUrl;

    public boolean isBookAvailable(Long bookId, LocalDate start, LocalDate end) {
        BookAvailabilityResponse response = webClient
                .get()
                .uri(bookServiceUrl + "/internal/books/{bookId}/available?start={start}&end={end}",
                        bookId, start, end)
                .retrieve()
                .bodyToMono(BookAvailabilityResponse.class)
                .block();

        return response != null && response.isAvailable();
    }

    public BookDto getBookById(Long bookId) {
        return webClient
                .get()
                .uri(bookServiceUrl + "/books/{id}", bookId)
                .retrieve()
                .bodyToMono(BookDto.class)
                .block();
    }

}
