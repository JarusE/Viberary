package com.example.bookservice.repository;

import com.example.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookRepository is a data access interface for managing Book entities in the persistence layer.
 * It provides CRUD operations and query methods for the Book entity by extending JpaRepository.
 *
 * JpaRepository is a Spring Data interface that includes standard methods such as save, findById,
 * findAll, deleteById, and more for managing entities.
 *
 * Type Parameters:
 * - Book: The entity type that this repository manages.
 * - Long: The type of the primary key used for the Book entity.
 *
 * Functionalities:
 * - Allows retrieval of all books, single books by ID, and saving or updating books.
 * - Enables deletion of books by ID.
 * - Supports custom query methods through defined method signatures when required.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
