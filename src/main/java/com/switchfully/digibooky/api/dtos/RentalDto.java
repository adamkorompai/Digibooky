package com.switchfully.digibooky.api.dtos;

import java.time.LocalDate;

public class RentalDto {

    private  String id;
    private  String bookIsbn;
    private  String userId;
    private  LocalDate rentalDate;
    private  LocalDate dueDate;
    private  LocalDate returnDate;

    public RentalDto(){}


    public RentalDto(String id,String bookIsbn, String userId, LocalDate rentalDate, LocalDate dueDate) {
        this.id = id;
        this.bookIsbn = bookIsbn;
        this.userId = userId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }



    public String getId() {
        return String.valueOf(id);
    }
}
