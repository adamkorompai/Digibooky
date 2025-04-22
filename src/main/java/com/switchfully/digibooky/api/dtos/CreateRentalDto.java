package com.switchfully.digibooky.api.dtos;

import java.time.LocalDate;

public class CreateRentalDto {
    private String bookIsbn;
    private String userId;
    private LocalDate rentDate;


    public CreateRentalDto() {
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }
}
