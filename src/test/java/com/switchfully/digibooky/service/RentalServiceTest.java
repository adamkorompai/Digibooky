package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static io.restassured.RestAssured.when;

public class RentalServiceTest {

    @MockitoBean
    private RentalService rentalService;
    @MockitoBean
    private RentalRepository rentalRepository;
    @MockitoBean
    private RentalMapper rentalMapper;

    //Todo : CreateRental => Should be created

    @Test
    public void whenServiceCreateRental_thenRentalCreated() {



    }

    // Book must exist or Illegal Argument
    @Test
    public void whenBookIsNull_thenThrowException() {}

    @Test
    public void whenBookIsEmpty_thenThrowException() {}


    // User must Exist or Illegal Argument
    @Test
    public void whenUserIsNull_thenThrowException() {}

    @Test
    public void whenUserIsEmpty_thenThrowException() {}

    // BookCopy should be positive and superior to 0

    @Test
    public void whenRenting_givingNumberOfBooksCopyMinus1_ThenRentalNotRented() {}

    @Test
    public void whenRenting_givingNumberOfBooksCopy0_ThenRentalNotRented() {}


    // Test remove copy of book when rent is created

    @Test
    public void whenRenting_numberOfBooksDecrease() {}


    // Due date is created 3 Weeks after rentalDate

    @Test
    public void whenRenting_DueDateMustBe3WeeksAfterRentalDate() {}


    // Todo : Return Rent => Rent should have a returnDate and have the state is Returned

    @Test
    public void whenReturnig_thenRentHaveReturnDate_AndStateChange() {}

    //  Should send a message Because Rent is late

    @Test
    public void whenReturnigIsLate_thenMessage() {}
    @Test
    public void whenReturnigNotLate_thenNoMessage() {}

    //  adding a Copy of books

    @Test
    public void whenReturning_numberOfBooksIncrease() {}

}
