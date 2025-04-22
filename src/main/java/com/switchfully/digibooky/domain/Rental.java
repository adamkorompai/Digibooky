package com.switchfully.digibooky.domain;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Rental {

    private final long id;
    private final String bookIsbn;
    private final String userId;
    private final LocalDate rentalDate;
    private final LocalDate dueDate;
    private  LocalDate returnedDate;

    private RentalState rentalState;

    private static long nextId = 1;

    public Rental(String bookIsbn, String userId, LocalDate rentalDate, LocalDate returnDate) {
        this.id = nextId++;
        this.bookIsbn = bookIsbn;
        this.userId = userId;
        this.rentalDate = rentalDate;
        this.dueDate =returnDate;
        rentalState = RentalState.IN_RENT;
    }



    public String getId() {
        return String.valueOf(id);
    }

    public void returnRental(){
        LocalDate date = LocalDate.now();
        if(date.isAfter(dueDate)){
            rentalState = RentalState.LATE_RETURN;
        }
        else{
            rentalState = RentalState.RETURNED_AT_TIME;
        }
    }

    //TODO : OVERDUE BOOK DONC je dois refaire le calcul pour savoir si un livre encore en mode
    // Rented
    public RentalState getRentalState() {
        return rentalState;
    }
    public boolean isLateRental(){
        return rentalState == RentalState.LATE_RETURN;
    }
    public boolean isReturnRental(){
        return rentalState == RentalState.RETURNED_AT_TIME;
    }

    public void setRentalState(RentalState rentalState) {
        this.rentalState = rentalState;
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

    public static long getNextId() {
        return nextId;
    }
}
