package com.example.bookservice.service;

import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * Service class responsible for fetching book data from an external API and saving it
 * into the database. This class integrates with the Google Books API and the application's
 * persistence layer to populate the book repository if no data currently exists.
 *
 * Responsibilities:
 * - Checks whether the book repository already contains data.
 * - Makes HTTP requests to the Google Books API to fetch books related to the subject of fiction.
 * - Maps the fetched JSON response to Java objects using the GoogleBooksResponse model.
 * - Saves valid book data to the repository with necessary attributes such as title, author,
 *   genre, description, preview link, total copies, and availability status.
 * - Sets default values for unspecified attributes such as genre and the number of copies available.
 *
 * Key Method:
 * - fetchAndSaveBooks: Implements the logic to fetch book data from the external API and store it
 *   in the book repository. Logs messages indicating the success or failure of the operations.
 *
 * Dependencies:
 * - BookRepository: The data access interface for CRUD operations on the Book entity.
 * - RestTemplate: A Spring component used to perform RESTful API calls.
 */
@Service
@RequiredArgsConstructor
public class BookFetcherService {
    private final BookRepository bookRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void fetchAndSaveBooks() {
        if (bookRepository.count() > 0) {
            System.out.println("The book database is already stored");
            return;
        }

        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:fiction&maxResults=5";

        var response = restTemplate.getForObject(url, GoogleBooksResponse.class);

        if (response != null && response.items != null) {
            response.items.forEach(item -> {
                var volumeInfo = item.volumeInfo;
                if (volumeInfo.title != null && volumeInfo.authors != null) {
                    bookRepository.save(Book.builder()
                            .title(volumeInfo.title)
                            .author(String.join(", ", volumeInfo.authors))
                            .genre(volumeInfo.categories != null ? String.join(", ", volumeInfo.categories) : "Unknown")
                            .description(volumeInfo.description)
                            .demoUrl(volumeInfo.previewLink)
                            .totalCopies(5)
                            .availableCopies(5)
                            .available(true)
                            .createdAt(LocalDateTime.now())
                            .build());
                }
            });

            System.out.println("Books successfully loaded from API.");
        } else {
            System.out.println("Error retrieving data from API.");
        }
    }

}
