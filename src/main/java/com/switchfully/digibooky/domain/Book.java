package com.switchfully.digibooky.domain;

public class Book {
    private String isbn;
    private String title;
    private Author author;
    private String summary;

    public Book(String isbn, Author author, String title, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
