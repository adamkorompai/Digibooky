package com.switchfully.digibooky.service;

import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        logger.info("Getting all books");
        return bookRepository.getAllBooks();
    }
}
