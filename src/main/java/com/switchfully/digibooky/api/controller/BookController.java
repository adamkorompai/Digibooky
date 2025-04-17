package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/digibooky/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        logger.info("Request to get all books");
        List<Book> books = bookService.getAllBooks();
        return books.stream()
                .map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }
}
