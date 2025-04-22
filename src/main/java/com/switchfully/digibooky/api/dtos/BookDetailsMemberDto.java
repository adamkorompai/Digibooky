package com.switchfully.digibooky.api.dtos;

public class BookDetailsMemberDto {
    private String isbn;
    private String title;
    private AuthorDto author;
    private String summary;
    private boolean isBorrowed;
    private String borrowedByUserId; // can be null if not borrowed

    public BookDetailsMemberDto(String isbn, String title, AuthorDto author, String summary, boolean isBorrowed, String borrowedByUserId) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.isBorrowed = isBorrowed;
        this.borrowedByUserId = borrowedByUserId;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public String getBorrowedByUserId() {
        return borrowedByUserId;
    }
}
