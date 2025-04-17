package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;

public class BookDto {
    private String isbn;
    private String title;
    private AuthorDto author;
    private String summary;

    public BookDto() {}

    public BookDto(String isbn, String title, AuthorDto author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
