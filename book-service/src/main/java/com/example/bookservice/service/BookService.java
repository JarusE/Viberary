package com.example.bookservice.service;

import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for managing the business logic related to Book entities.
 * This class acts as an intermediary between the controller layer and the data
 * persistence layer, providing functionality for CRUD operations and business
 * logic on books.
 *
 * Responsibilities:
 * - Retrieve all books from the repository.
 * - Find a specific book by its ID.
 * - Save a new book into the repository with additional attributes such as creation time,
 *   available copies, and availability status.
 * - Update an existing book by its ID.
 * - Delete a book from the repository by its ID.
 *
 * Dependencies:
 * - BookRepository: A Spring Data JPA repository that handles Book entity persistence
 *   and query operations.
 *
 * Methods:
 * - findAll: Retrieves a list of all books.
 * - findById: Retrieves a book by its unique ID.
 * - save: Adds a new book to the repository. Automatically sets the creation time,
 *   available copies, and availability status.
 * - update: Updates an existing book using the provided ID and new attributes.
 * - delete: Removes a book from the repository using its ID.
 */
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book save(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setAvailableCopies(book.getTotalCopies());
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
