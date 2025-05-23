package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
    private final Map<Long, Book> bookDatabase;

    public BookRepository() {
        bookDatabase = new HashMap<>();
        initializeBooks();
    }

    private void initializeBooks() {
        Author author1 = new Author("Jane", "Doe");
        Author author2 = new Author("John", "Doe");

        Book book1 = new Book("978-1-23-456789-7", author1, "The Great Adventure" , "A thrilling adventure story.",0);
        Book book2 = new Book("978-9-87-654321-0", author1, "Mystery of the Old Manor", "A mysterious tale of an old manor.",3);
        Book book3 = new Book("978-0-12-345678-9", author2, "Programming Basics" ,"Learn programming fundamentals.",4);

        bookDatabase.put(book1.getId(), book1);
        bookDatabase.put(book2.getId(), book2);
        bookDatabase.put(book3.getId(), book3);
    }

    public void saveBook(Book book) {
        bookDatabase.put(book.getId(), book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookDatabase.values());
    }

    public Book getBookById(long id) {
        System.out.println(bookDatabase.values());
        return bookDatabase.get(id);
    }

    public Book getBookByIsbn(String isbn) {
        log.info("Searching for book with isbn {}", isbn);
        Book book = bookDatabase.values().stream().filter(bk -> {
            System.out.println(bk.getIsbn());
            return bk.getIsbn().equals(isbn);
        }).findFirst().orElse(null);
        log.info("book Value : " + book);
        return book;
    }

    public void removeCopyOfBook(long bookId) {
        Book book = bookDatabase.get(bookId);
        if (book != null ) {
            int currentCopies = book.getNumberOfCopy();
            book.setNumberOfCopy(currentCopies - 1);
        }
    }

    public List<Book> searchBooksByIsbn(String isbn) {
        String regex = isbn.replace("*", ".*").replace("?", ".");
        Pattern pattern = Pattern.compile(regex);

        return bookDatabase.values().stream()
                .filter(book -> pattern.matcher(book.getIsbn()).matches())
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String title) {
        String regex = title.replace("*", ".*").replace("?", ".");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        return bookDatabase.values().stream()
                .filter(book -> pattern.matcher(book.getTitle()).matches())
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByAuthor(String author) {
        String regex = author.replace("*", ".*").replace("?", ".");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        return bookDatabase.values().stream()
                .filter(book -> {
                    String fullname = book.getAuthor().getFirstname() + " " + book.getAuthor().getLastname();
                    return  pattern.matcher(fullname).matches() ||
                            pattern.matcher(book.getAuthor().getFirstname()).matches() ||
                            pattern.matcher(book.getAuthor().getLastname()).matches();
                })
                .collect(Collectors.toList());
    }


}
