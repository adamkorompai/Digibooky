package com.switchfully.digibooky.domain;

public class Book {
    private long id;
    private String isbn;
    private String title;
    private Author author;
    private String summary;


    private static long nextId = 1;

    public Book(String isbn, Author author, String title, String summary) {
        this.id = nextId++;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                '}';
    }
}
