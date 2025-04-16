package com.switchfully.digibooky.domain;

public class Book {
    private String isbn;
    private String title;
    private Author author;

    public Book(String isbn, Author author, String title) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
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

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
