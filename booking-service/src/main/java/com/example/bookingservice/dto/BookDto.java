package com.example.bookingservice.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) that represents a book's information.
 * This class encapsulates core details about a book, including its
 * metadata, availability, and descriptions, and is primarily used for
 * communication between services or layers in the application.
 *
 * Fields in this DTO include:
 * - id: The unique identifier of the book.
 * - title: The title of the book.
 * - author: The author of the book.
 * - genre: The genre of the book.
 * - description: A brief description of the book.
 * - demoUrl: A URL pointing to a demo or preview of the book.
 * - totalCopies: The total number of copies of the book available in the system.
 * - availableCopies: The number of copies currently available for usage.
 * - available: A boolean indicating whether the book is generally available.
 */
@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String demoUrl;
    private int totalCopies;
    private int availableCopies;
    private boolean available;
}
