package com.switchfully.digibooky.api.dtos;

public class BookDetailsDto {
    private String isbn;
    private String title;
    private AuthorDto author;
    private String summary;

    public BookDetailsDto(String isbn, String title, AuthorDto author, String summary) {
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
}
