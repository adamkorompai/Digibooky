package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RentalRepositoryTest {


    private RentalRepository rentalRepository;

    @BeforeEach
    public void setUp() {
        rentalRepository = new RentalRepository();
    }


    //addRental

    @Test
    public void testAddRental() {
        int initialSize = rentalRepository.getRentals().size();
        rentalRepository.addRental(new Rental("asbnt","4", LocalDate.now(),LocalDate.now()));
        assertEquals(rentalRepository.getRentals().size(), initialSize + 1);

    }

    @Test
    public void testGetRentals() {

        Rental rental = new Rental("asbnt","4", LocalDate.now(),LocalDate.now());

        rentalRepository.addRental(rental);
        assertTrue(rentalRepository.getRentals().containsValue(rental));

    }




}
