package com.switchfully.digibooky.service;


import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BookServiceTest {
    private BookService bookService;
    private BookRepository bookRepository;
    private BookMapper bookMapper;

//    The books created in the repo:
//    Author author1 = new Author("Jane", "Doe");
//    Author author2 = new Author("John", "Doe");
//
//    Book book1 = new Book("978-1-23-456789-7", author1, "The Great Adventure" , "A thrilling adventure story.");
//    Book book2 = new Book("978-9-87-654321-0", author1, "Mystery of the Old Manor", "A mysterious tale of an old manor.");
//    Book book3 = new Book("978-0-12-345678-9", author2, "Programming Basics" ,"Learn programming fundamentals.");

    @BeforeEach
    public void setUp() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository, new BookMapper());
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks();

        assertEquals(3, allBooks.size());
        assertEquals("The Great Adventure", allBooks.get(0).getTitle());
        assertEquals("Mystery of the Old Manor", allBooks.get(1).getTitle());
        assertEquals("Programming Basics", allBooks.get(2).getTitle());
    }

//    @Test
//    void getBookById_ShouldReturnBook() {
//        long id = 1;
//        BookDto result = bookService.getBookById(id);
//
//        assertNotNull(result);
//        assertEquals(bookRepository.getAllBooks().get(0).getIsbn(), result.getIsbn());
//        assertEquals(bookRepository.getAllBooks().get(0).getTitle(), result.getTitle()); A VERIFIER
//    }

    @Test
    void searchBooksByIsbn_shouldReturnMatchingBooks() {
        List<BookDto> books = bookService.searchBooksByIsbn("978*");

        assertEquals(3, books.size());
        assertEquals("The Great Adventure", books.get(0).getTitle());
        assertEquals("Mystery of the Old Manor", books.get(1).getTitle());
    }

    @Test
    void getBookByIsbn_whenNoMatch_shouldReturnEmptyList() {
        List<BookDto> books = bookService.searchBooksByIsbn("123*");

        assertTrue(books.isEmpty());
    }
}
