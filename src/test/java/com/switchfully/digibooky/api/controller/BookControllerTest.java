package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.AuthorDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.service.BookService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    private static final Logger log = LoggerFactory.getLogger(BookControllerTest.class);
    @LocalServerPort
    private int port;

    @Autowired
    private BookService bookService;

    @Nested
    class GetRequest {

        @Test
        public void getAllBooks_shouldReturnListOfBooks() {
            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .get("/digibooky/books")
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThanOrEqualTo(2))
                    .body("[0].isbn", notNullValue())
                    .body("[0].title", notNullValue())
                    .body("[0].author", notNullValue())
                    .body("[0].summary", notNullValue());
        }

        @Test
        public void getBookById_shouldReturnBook() {
            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .get("/digibooky/books/1")
                    .then()
                    .statusCode(200)
                    .body("isbn", notNullValue())
                    .body("title", notNullValue())
                    .body("author", notNullValue())
                    .body("summary", notNullValue());
        }

        @Test
        public void getBookById_shouldReturnTheRightBook() {
            String id = "1";
            BookDto book = bookService.getBookById(Long.parseLong(id));
            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .get("/digibooky/books/{id}", id)
                    .then()
                    .statusCode(200)
                    .body("isbn", equalTo(book.getIsbn()))
                    .body("title", equalTo(book.getTitle()));
        }

        @Test
        public void searchBooksByIsbn_shouldReturnListOfBooks() {

            String id = "1";

            AuthorDto author1 = new AuthorDto("John", "Doe");
            AuthorDto author2 = new AuthorDto("Jane", "Smith");

            BookDto book1 = new BookDto("978-0-306-40615-7", "Book Title 1", author1, "Summary of Book 1", 3);
//            BookDto book2 = new BookDto("978-1-23-456254-8", "Book Title 2", author2, "Summary of Book 2", 5);


            bookService.createBook(book1);
//            bookService.createBook(book2);


            List<BookDto> mockBooks = bookService.searchBooksByIsbn(bookService.getBookById(Long.parseLong(id)).getIsbn());


            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .queryParam("isbn", book1.getIsbn())
                    .get("/digibooky/books/search/isbn")
                    .then()
                    .statusCode(200)
                    .body("$.size()", equalTo(mockBooks.size())) // Validate list size
                    .body("[0].isbn", equalTo(book1.getIsbn()))  // Validate first book's ISBN
                    .body("[0].title", equalTo(book1.getTitle())); // Validate first book's title
//                    .body("[1].isbn", equalTo(book2.getIsbn()))  // Validate second book's ISBN
//                    .body("[1].title", equalTo(book2.getTitle())); // Validate second book's title
        }

        @Test
        public void searchBooksByTitle_shouldReturnListOfBooks() {

            String id = "1";

            AuthorDto author1 = new AuthorDto("John", "Doe");
            AuthorDto author2 = new AuthorDto("Jane", "Smith");

            BookDto book1 = new BookDto("978-0-306-40615-7", "Book Title 1", author1, "Summary of Book 1", 3);
//            BookDto book2 = new BookDto("978-1-23-456254-8", "Book Title 2", author2, "Summary of Book 2", 5);


            bookService.createBook(book1);
//            bookService.createBook(book2);


            List<BookDto> mockBooks = bookService.searchBooksByTitle(bookService.getBookById(Long.parseLong(id)).getTitle());


            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .queryParam("title", book1.getTitle())
                    .get("/digibooky/books/search/title")
                    .then()
                    .statusCode(200)
                    .body("$.size()", equalTo(mockBooks.size())) // Validate list size
                    .body("[0].isbn", equalTo(book1.getIsbn()))  // Validate first book's ISBN
                    .body("[0].title", equalTo(book1.getTitle())); // Validate first book's title
//                    .body("[1].isbn", equalTo(book2.getIsbn()))  // Validate second book's ISBN
//                    .body("[1].title", equalTo(book2.getTitle())); // Validate second book's title
        }@Test
        public void searchBooksByAuthor_shouldReturnListOfBooks() {

            String id = "1";

            AuthorDto author1 = new AuthorDto("Jack", "Smith");
            AuthorDto author2 = new AuthorDto("Jane", "Smith");

            BookDto book1 = new BookDto("978-0-306-40615-7", "Book Title 1", author1, "Summary of Book 1", 3);
//            BookDto book2 = new BookDto("978-1-23-456254-8", "Book Title 2", author2, "Summary of Book 2", 5);


            bookService.createBook(book1);
//            bookService.createBook(book2);


            List<BookDto> mockBooks = bookService.searchBooksByAuthor(bookService.getBookById(Long.parseLong(id)).getAuthor().getFirstname());
            log.info("Found {} books", mockBooks);


            given()
                    .port(port)
                    .contentType("application/json")
                    .when()
                    .queryParam("author", book1.getAuthor().getLastname())
                    .get("/digibooky/books/search/author")
                    .then()
                    .statusCode(200)
                    .body("[0].isbn", equalTo(book1.getIsbn()))  // Validate first book's ISBN
                    .body("[0].title", equalTo(book1.getTitle())); // Validate first book's title
//                    .body("[1].isbn", equalTo(book2.getIsbn()))  // Validate second book's ISBN
//                    .body("[1].title", equalTo(book2.getTitle())); // Validate second book's title
        }
    }


}
