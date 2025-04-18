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


}
