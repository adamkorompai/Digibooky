package com.switchfully.digibooky.api.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

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
        public void getBookByIsbn_shouldReturnBook() {

        }

    }
}
