package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final MemberMapper memberMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, MemberMapper memberMapper) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.memberMapper = memberMapper;
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

    public BookDto updateBook(String isbn, BookDto bookDto) {
        logger.info("Updating book {}", bookDto.getIsbn());
        Book book = bookRepository.getBookByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("There is no book with this ISBN in the database");
        }
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookMapper.mapToAuthor(bookDto.getAuthor()));
        book.setSummary(bookDto.getSummary());
        book.setNumberOfCopy(bookDto.getNumberOfCopy());
        return bookMapper.mapToBookDto(book);
    }

    public void deleteBook(String isbn) {
        logger.info("Deleting book {}", isbn);
        Book book = bookRepository.getBookByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("There is no book with this ISBN in the database");
        }
        book.setNumberOfCopy(-1);
        bookRepository.saveBook(book);
    }

    private void validateBook(BookDto bookDto) {
        validateNotNullEmptyFields(bookDto.getTitle(), "Title is required");
        validateNotNullEmptyFields(bookDto.getAuthor().getLastname(), "Author is required");
        validateNotNullEmptyFields(bookDto.getIsbn(), "Isbn is required");
        if(!validateISBN13(bookDto.getIsbn())){
            throw new IllegalArgumentException("ISBN must follow the ISBN 13");
        };
    }

    private void validateNotNullEmptyFields(String Field, String errorMessage) {
        if (Field == null || Field.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    // Method to validate ISBN-13 , tried with adding following isbn 978-92-95055-02-5, do not work even if valid isbn..
    public boolean validateISBN13(String isbn) {
        // Step 1: Check format using regex
        String regex = "^(978|979)-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d{1}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(isbn);

        if (!matcher.matches()) {
            return false;  // ISBN doesn't match the format
        }

        // Step 2: Remove hyphens and perform checksum validation
        isbn = isbn.replace("-", "");

        // Ensure we have 13 digits
        if (isbn.length() != 13) {
            return false;
        }

        // Step 3: Perform checksum validation
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            // Multiply by 1 or 3 depending on position (even positions: multiply by 1, odd: multiply by 3)
            if (i % 2 == 0) {
                sum += digit;  // Even positions (0, 2, 4, ...) are multiplied by 1
            } else {
                sum += digit * 3;  // Odd positions (1, 3, 5, ...) are multiplied by 3
            }
        }

        // Step 4: Calculate the check digit (last digit)
        int checkDigit = Character.getNumericValue(isbn.charAt(12));
        int remainder = sum % 10;
        int expectedCheckDigit = (10 - remainder) % 10;


        // Step 5: Compare the calculated check digit with the last digit of the ISBN
        return expectedCheckDigit == checkDigit;
    }


}
