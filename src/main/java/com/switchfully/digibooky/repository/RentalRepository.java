package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RentalRepository {
    private final ConcurrentHashMap<String, Rental> rentals;
    public RentalRepository() {
        rentals = new ConcurrentHashMap<>();

    }

    public Rental addRental(Rental rental) {
        return rentals.put(rental.getId(), rental);
    }

    public Rental getRental(String id) {
        return rentals.get(id);
    }
}
