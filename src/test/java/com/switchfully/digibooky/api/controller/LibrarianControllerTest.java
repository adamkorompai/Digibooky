package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.AuthorDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibrarianControllerTest {

    private static final Logger log = LoggerFactory.getLogger(LibrarianControllerTest.class);
    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BookService bookService;
    //    The books created in the repo:
//    Author author1 = new Author("Jane", "Doe");
//    Author author2 = new Author("John", "Doe");
//
//    Book book1 = new Book("978-1-23-456789-7", author1, "The Great Adventure" , "A thrilling adventure story.",2);
//    Book book2 = new Book("978-9-87-654321-0", author1, "Mystery of the Old Manor", "A mysterious tale of an old manor.",3);
//    Book book3 = new Book("978-0-12-345678-9", author2, "Programming Basics" ,"Learn programming fundamentals.",4);




    // Create Book Test
    //TODO : Auth test
    @Test
    public void whenCallingCreateABook_ThenBookCreated() {
        AuthorDto authorA = new AuthorDto("George", "Orwell");
        BookDto bookDto = new BookDto("978-9-29505-502-5", "The Great Project", authorA, "A nice project story.", 3);

        given()
                .port(port)
                .contentType("application/json")
                .when()
                .body(bookDto)
                .post("/digibooky/librarian/books")
                .then()
                .statusCode(201)
                .equals(bookDto);

    }

    //Update Book Test
    @Test
    public void whenCallingUpdateBook_ThenBookUpdated() {
        String isbn = "978-1-23-456789-7";
        Book book = bookRepository.getBookByIsbn(isbn);
        log.info("Book data in Test {}", book);
        log.info("Book data in Test {}", bookRepository.getAllBooks());

        BookDto dto = bookMapper.mapToBookDto(book);
        dto.setTitle("The Lord of the Rings");
        log.info("Book data in Test {}", bookRepository.getAllBooks());

        given()
                .port(port)
                .contentType("application/json")
                .when()
                .body(dto)
                .put("/digibooky/librarian/books/{isbn}", isbn)
                .then()
                .statusCode(200)
                .body("isbn", equalTo(book.getIsbn()))
                .body("title", equalTo(dto.getTitle()));

    }


    //Delete Book Test
    @Test
    public void whenCallingDeleteBook_ThenBookDeleted() {
        String isbn = "978-1-23-456789-7";


        given()
                .port(port)
                .contentType("application/json")
                .when()

                .delete("/digibooky/librarian/books/{isbn}", isbn)
                .then()
                .statusCode(204);

        assertEquals(4, bookRepository.getAllBooks().size());

    }

}
