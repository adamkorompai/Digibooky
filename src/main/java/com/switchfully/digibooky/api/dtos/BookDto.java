package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;

public class BookDto {
    private String isbn;
    private String title;
    private AuthorDto author;
    private String summary;
    private int numberOfCopy;

    public BookDto() {}

    public BookDto(String isbn, String title, AuthorDto author, String summary,int numberOfCopy) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.numberOfCopy = numberOfCopy;
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

    public int getNumberOfCopy() {
        return numberOfCopy;
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

    @Override
    public String toString() {
        return "BookDto{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                ", numberOfCopy=" + numberOfCopy +
                '}';
    }
}
