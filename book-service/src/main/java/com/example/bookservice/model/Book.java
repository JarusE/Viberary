package com.example.bookservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a Book entity in the system with attributes such as title, author, genre,
 * description, demo URL, and availability details. This entity is managed and persisted
 * by JPA and is used across multiple layers within the application.
 *
 * Fields:
 * - id: The unique identifier for the book.
 * - title: The title of the book.
 * - author: The name of the author(s) of the book.
 * - genre: The genre or category of the book.
 * - description: A detailed description of the book.
 * - demoUrl: A URL for a preview or demo of the book.
 * - totalCopies: The total number of copies of the book available in the system.
 * - availableCopies: The number of book copies currently available.
 * - available: Indicates whether the book is available for use or checkout.
 * - createdAt: The timestamp when the book entity was created.
 *
 * Annotations:
 * - @Entity: Specifies that this class is an entity and is mapped to a database table.
 * - @Data: A Lombok annotation to generate boilerplate code such as getters, setters,
 *          toString, equals, and hashCode methods.
 * - @NoArgsConstructor: Generates a no-arguments constructor.
 * - @AllArgsConstructor: Generates a constructor with all fields as arguments.
 * - @Builder: Enables the builder pattern for creating instances of the class.
 * - @Column: Used for customization of the column mapping, such as setting a custom
 *            length for the description field.
 * - @Id: Marks the primary key field of the entity.
 * - @GeneratedValue: Specifies the generation strategy for primary key values.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;

    @Column(length = 100000)
    private String description;

    private String demoUrl;

    private int totalCopies;
    private int availableCopies;

    private boolean available;
    private LocalDateTime createdAt;
}
