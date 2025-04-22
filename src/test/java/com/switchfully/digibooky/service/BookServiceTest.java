package com.switchfully.digibooky.service;


import com.switchfully.digibooky.api.dtos.AuthorDto;
import com.switchfully.digibooky.api.dtos.BookDetailsDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
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
//    Book book1 = new Book("978-1-23-456789-7", author1, "The Great Adventure" , "A thrilling adventure story.",2);
//    Book book2 = new Book("978-9-87-654321-0", author1, "Mystery of the Old Manor", "A mysterious tale of an old manor.",3);
//    Book book3 = new Book("978-0-12-345678-9", author2, "Programming Basics" ,"Learn programming fundamentals.",4);

    @BeforeEach
    public void setUp() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository, new BookMapper(), new MemberMapper());
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks();

        assertEquals(3, allBooks.size());
        assertEquals("The Great Adventure", allBooks.get(0).getTitle());
        assertEquals("Mystery of the Old Manor", allBooks.get(1).getTitle());
        assertEquals("Programming Basics", allBooks.get(2).getTitle());
    }

    @Test
    void getBookById_ShouldReturnBook() {
        long id = 1;
        BookDetailsDto result = bookService.getBookDetailsById(id);

        assertNotNull(result);
        assertEquals(bookRepository.getAllBooks().get(0).getIsbn(), result.getIsbn());
        assertEquals(bookRepository.getAllBooks().get(0).getTitle(), result.getTitle());
    }

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

    @Test
    void createBook_shouldCreateNewBook() {
        AuthorDto authorA = new AuthorDto("George", "Orwell");
        BookDto bookDto = new BookDto("978-92-95055-02-5", "The Great Project",authorA , "A nice project story.",3);

        BookDto bookCreated = bookService.createBook(bookDto);

        assertEquals("The Great Project", bookCreated.getTitle());
        assertEquals("978-92-95055-02-5", bookCreated.getIsbn());
        assertEquals(authorA, bookCreated.getAuthor());
        assertEquals("A nice project story.", bookCreated.getSummary());

    }

    @Test
    void updateBook_shouldUpdateExistingBook() {
        AuthorDto authorA = new AuthorDto("George", "Orwell");
        BookDto bookDto = new BookDto("978-1-23-456789-7", "The Great Project",authorA , "A nice project story.",3);

        BookDto bookUpdated = bookService.updateBook("978-1-23-456789-7", bookDto);

        assertEquals("The Great Project", bookUpdated.getTitle());
        assertEquals("978-1-23-456789-7", bookUpdated.getIsbn());
        assertEquals(authorA, bookUpdated.getAuthor());
        assertEquals("A nice project story.", bookUpdated.getSummary());

    }

    @Test
    void deleteBook_shouldDeleteExistingBook() {
        Book book = bookRepository.getBookByIsbn("978-1-23-456789-7");

        bookService.deleteBook("978-1-23-456789-7");

         assertEquals(-1,book.getNumberOfCopy() );
    }

    @Test
    void checkISBN13_shouldReturnTrueForValidISBN() {
        String validISBN = "978-92-95055-02-5";

        boolean checkResult = bookService.validateISBN13(validISBN);
        assertTrue(checkResult);
    }

    @Test
    void checkISBN13_shouldReturnFalseForInvalidFormat() {
        String validISBN = "978-92-95-5";

        boolean checkResult = bookService.validateISBN13(validISBN);
        assertFalse(checkResult);
    }
}
