package com.example.bookservice.service;

import java.util.List;

/**
 * Represents the response structure from the Google Books API. This model is used to map the
 * JSON response into Java objects, ensuring the application can process the data retrieved
 * from the API. The response contains a list of items, where each item includes volume
 * information about a specific book.
 *
 * Structure:
 * - items: A list of books retrieved from the Google Books API. Each book is represented as
 *   an Item containing volume information.
 *
 * Nested Classes:
 * - Item: Represents an individual item in the API response. Contains detailed volume data.
 * - VolumeInfo: Contains details about a book, such as its title, authors, description,
 *   categories, and a preview link.
 */
public class GoogleBooksResponse {
    public List<Item> items;

    public static class Item {
        public VolumeInfo volumeInfo;
    }

    public static class VolumeInfo {
        public String title;
        public List<String> authors;
        public String description;
        public List<String> categories;
        public String previewLink;
    }
}
