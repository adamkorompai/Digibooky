package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.Rental;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class BookMapperTest {
    private final BookMapper bookMapper = new BookMapper();

    @Test
    public void mapToBookDto_shouldConvertToBookDtoCorrectly() {
        Author author = new Author("John", "Doe");
        Book book = new Book("9781234567897", author, "Test book", "A summary",2);

        BookDto result = bookMapper.mapToBookDto(book);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.getNumberOfCopy()).isEqualTo(2);
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getFirstname()).isEqualTo("John");
        assertThat(result.getAuthor().getLastname()).isEqualTo("Doe");
    }

    @Test
    public void mapToBook_shouldConvertToBookCorrectly() {
        AuthorDto author = new AuthorDto("John", "Doe");
        BookDto book = new BookDto("9781234567897", "Test book", author, "A summary",2);

        Book result = bookMapper.mapToBook(book);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.getNumberOfCopy()).isEqualTo(2);
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getFirstname()).isEqualTo("John");
        assertThat(result.getAuthor().getLastname()).isEqualTo("Doe");
    }

    @Test
    public void mapToBookDetailsDto_shouldConvertToBookDetailsDtoCorrectly() {
        Author author = new Author("John", "Doe");
        Book book = new Book("9781234567897", author, "Test book","A summary", 2);

        BookDetailsDto result = bookMapper.mapToBookDetailsDto(book);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getFirstname()).isEqualTo("John");
        assertThat(result.getAuthor().getLastname()).isEqualTo("Doe");
    }

    @Test
    public void mapToBookDetailsMemberDto_whenBookIsNotBorrowed_shouldSetBorrowedFalseAndNullUserId() {
        AuthorDto author = new AuthorDto("John", "Doe");
        BookDto book = new BookDto("9781234567897", "Test book", author, "A summary", 2);
        Rental rental = null;

        BookDetailsMemberDto result = bookMapper.mapToBookDetailsMemberDto(book, rental);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getFirstname()).isEqualTo("John");
        assertThat(result.getAuthor().getLastname()).isEqualTo("Doe");
        assertThat(result.isBorrowed()).isFalse();
        assertThat(result.getBorrowedByUserId()).isNull();
    }

    @Test
    public void mapToBookDetailsMemberDto_whenBookIsBorrowed_shouldSetBorrowedTrueAndCorrectUserId() {
        AuthorDto author = new AuthorDto("John", "Doe");
        BookDto book = new BookDto("9781234567897", "Test book", author, "A summary", 2);

        LocalDate rentalDate = LocalDate.now();
        LocalDate dueDate = rentalDate.plusWeeks(3);
        Rental rental = new Rental("9781234567897", "user123", rentalDate, dueDate);

        BookDetailsMemberDto result = bookMapper.mapToBookDetailsMemberDto(book, rental);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getFirstname()).isEqualTo("John");
        assertThat(result.getAuthor().getLastname()).isEqualTo("Doe");
        assertThat(result.isBorrowed()).isTrue();
        assertThat(result.getBorrowedByUserId()).isEqualTo("user123");
    }

    @Test
    public void mapToBookDetailsMemberDto_whenBookIsReturnedAtTime_shouldSetBorrowedFalseAndNullUserId() {
        AuthorDto author = new AuthorDto("John", "Doe");
        BookDto book = new BookDto("9781234567897", "Test book", author, "A summary", 2);

        LocalDate rentalDate = LocalDate.now().minusWeeks(2);
        LocalDate dueDate = rentalDate.plusWeeks(3);
        Rental rental = new Rental("9781234567897", "user123", rentalDate, dueDate);
        rental.returnRental();

        BookDetailsMemberDto result = bookMapper.mapToBookDetailsMemberDto(book, rental);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.isBorrowed()).isFalse();
        assertThat(result.getBorrowedByUserId()).isNull();
    }

    @Test
    public void mapToBookDetailsMemberDto_whenBookHasLateReturn_shouldSetBorrowedFalseAndNullUserId() {
        AuthorDto author = new AuthorDto("John", "Doe");
        BookDto book = new BookDto("9781234567897", "Test book", author, "A summary", 2);

        LocalDate rentalDate = LocalDate.now().minusWeeks(4);
        LocalDate dueDate = rentalDate.plusWeeks(3);
        Rental rental = new Rental("9781234567897", "user123", rentalDate, dueDate);
        rental.returnRental();

        BookDetailsMemberDto result = bookMapper.mapToBookDetailsMemberDto(book, rental);

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9781234567897");
        assertThat(result.getTitle()).isEqualTo("Test book");
        assertThat(result.getSummary()).isEqualTo("A summary");
        assertThat(result.isBorrowed()).isFalse();
        assertThat(result.getBorrowedByUserId()).isNull();
    }
}
