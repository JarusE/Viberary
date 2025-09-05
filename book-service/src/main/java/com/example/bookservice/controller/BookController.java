package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The BookController class is a REST controller responsible for managing CRUD operations
 * and endpoints related to books within the application. It handles various HTTP requests
 * for retrieving, creating, updating, and deleting books.
 *
 * It interacts with the BookService class to perform these operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        List<Book> books = bookService.findAll();
        System.out.println("[GET /books] Number of books found: " + books.size());
        for (Book book : books) {
            System.out.println("Book - " + book.getTitle() + " | " + book.getAuthor());
        }
        return books;
    }

    @GetMapping("/ping")
    public String ping() {
        return "book-service is working";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id) {
        System.out.println("[GET /books/" + id + "] Request a book by ID");
        Book book = bookService.findById(id);
        System.out.println("Found: " + book.getTitle() + " | " + book.getAuthor());
        return ResponseEntity.ok(book);
    }

    @PostMapping("/admin")
    public Book create(@RequestBody Book book) {
        System.out.println("[POST /books/admin] Creating a new book: " + book.getTitle());
        return bookService.save(book);
    }

    @PutMapping("/admin/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        System.out.println("[PUT /books/admin/" + id + "] Book update: " + book.getTitle());
        return bookService.update(id, book);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        System.out.println("[DELETE /books/admin/" + id + "] Deleting a book");
        bookService.delete(id);
    }
}
