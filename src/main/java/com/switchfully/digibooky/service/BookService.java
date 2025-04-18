package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        logger.info("Getting all books");
        return bookRepository.getAllBooks().stream().map(bookMapper::mapToBookDto).collect(Collectors.toList());
    }

    public BookDto getBookById(long id) {
        logger.info("Getting book by id {}", id);
        Book book = bookRepository.getBookById(id);
        return bookMapper.mapToBookDto(book);
    }

    public List<BookDto> searchBooksByIsbn(String isbn) {
        logger.info("Searching for books by ISBN");
        List<Book> books = bookRepository.searchBooksByIsbn(isbn);
        return bookMapper.mapToBookDtos(books);
    }

    public List<BookDto> searchBooksByTitle(String title) {
        logger.info("Searching for books by title {}", title);
        List<Book> books = bookRepository.searchBooksByTitle(title);
        return bookMapper.mapToBookDtos(books);
    }

    public List<BookDto> searchBooksByAuthor(String author) {
        logger.info("Searching for books by author {}", author);
        List<Book> books = bookRepository.searchBooksByAuthor(author);
        return bookMapper.mapToBookDtos(books);
    }

    public BookDto createBook(BookDto bookDto) {
        logger.info("Creating book {}", bookDto.getTitle());
        validateBook(bookDto);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.saveBook(book);
        return bookMapper.mapToBookDto(book);
    }

    private void validateBook(BookDto bookDto) {
        if (bookDto.getTitle() == null || bookDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (bookDto.getAuthor() == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        if (bookDto.getIsbn() == null) {
            throw new IllegalArgumentException("Isbn cannot be null");
        }
        if (bookDto.getAuthor().getLastname() == null) {
            throw new IllegalArgumentException("Lastname cannot be null");
        }
    }


}
