package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.junit.jupiter.api.Test;

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
}
