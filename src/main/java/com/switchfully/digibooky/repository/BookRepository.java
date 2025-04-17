package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {
    private final Map<String, Book> bookDatabase;

    public BookRepository() {
        bookDatabase = new HashMap<>();
        initializeBooks();
    }

    private void initializeBooks() {
        Author author1 = new Author("Jane", "Doe");
        Author author2 = new Author("John", "Doe");

        Book book1 = new Book("9781234567897", author1, "The Great Adventure" , "A thrilling adventure story.");
        Book book2 = new Book("9789876543210", author1, "Mystery of the Old Manor", "A mysterious tale of an old manor.");
        Book book3 = new Book("9780123456789", author2, "Programming Basics" ,"Learn programming fundamentals.");

        bookDatabase.put(book1.getIsbn(), book1);
        bookDatabase.put(book2.getIsbn(), book2);
        bookDatabase.put(book3.getIsbn(), book3);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookDatabase.values());
    }
}
