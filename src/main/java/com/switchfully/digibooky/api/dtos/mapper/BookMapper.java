package com.switchfully.digibooky.api.dtos.mapper;

import com.switchfully.digibooky.api.dtos.AuthorDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(author.getFirstname(), author.getLastname());
    }

    public Author mapToAuthor(AuthorDto authorDto) {
        return new Author(authorDto.getFirstname(), authorDto.getLastname());
    }

    public BookDto mapToBookDto(Book book) {
        AuthorDto authorDto = mapToAuthorDto(book.getAuthor());

        return new BookDto(book.getIsbn(), book.getTitle(), authorDto, book.getSummary());
    }

    public Book mapToBook(BookDto bookDto) {
        Author author = mapToAuthor(bookDto.getAuthor());

        return new Book(bookDto.getIsbn(), author, bookDto.getTitle(), bookDto.getSummary());
    }
}
