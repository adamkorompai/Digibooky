package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/digibooky/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        logger.info("Request to get all books");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable long id) {
        logger.info("Request to get book by id {}", id);
        return bookService.getBookById(id);
    }

    @GetMapping("/search/isbn")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksByIsbn(@RequestParam String isbn) {
        logger.info("Request to get all books by ISBN {}", isbn);
        return bookService.searchBooksByIsbn(isbn);
    }

    @GetMapping("/search/title")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksByTitle(@RequestParam String title) {
        logger.info("Request to get all books by title {}", title);
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/search/author")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBooksByAuthor(@RequestParam String author) {
        logger.info("Request to get all books by author {}", author);
        return bookService.searchBooksByAuthor(author);
    }
}
