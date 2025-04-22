package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.domain.RentalState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class RentalRepository {
    private final HashMap<String, Rental> rentals;
  
    public RentalRepository() {
         rentals = new HashMap<>();
         initializeTestData();
    }

    public Rental addRental(Rental rental) {
        return rentals.put(rental.getId(), rental);
    }

    public Rental getRental(String id) {
        return rentals.get(id);
    }
  
    public HashMap<String, Rental> getRentals() {
          return rentals;
    }

    public Rental getActiveRentalByIsbn(String isbn) {
        return rentals.values().stream()
                .filter(rental -> rental.getBookIsbn().equals(isbn) && rental.getRentalState() == RentalState.IN_RENT)
                .findFirst().orElse(null);
    }

    public List<Rental> getRentalsByUserId(String userId) {
        return rentals.values().stream()
                .filter(rental -> rental.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Rental> getOverdueRentals() {
        LocalDate today = LocalDate.now();
        return rentals.values().stream()
                .filter(rental -> rental.getReturnedDate() == null)
                .filter(rental -> rental.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    private void initializeTestData() {
        String userId1 = "user-123";
        String userId2 = "user-456";
        String userId3 = "user-789";
        String userId4 = "user-101";

        addRental(new Rental(
                "978-1234567890",
                userId1,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(11)
        ));

        addRental(new Rental(
                "978-2345678901",
                userId1,
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(16)
        ));

        addRental(new Rental(
                "978-3456789012",
                userId2,
                LocalDate.now().minusDays(15),
                LocalDate.now().plusDays(6)
        ));

        addRental(new Rental(
                "978-4567890123",
                userId3,
                LocalDate.now().minusDays(20),
                LocalDate.now().plusDays(1)
        ));

        addRental(new Rental(
                "978-5678901234",
                userId3,
                LocalDate.now().minusDays(18),
                LocalDate.now().plusDays(3)
        ));

        addRental(new Rental(
                "978-6789012345",
                userId3,
                LocalDate.now().minusDays(7),
                LocalDate.now().plusDays(14)
        ));

        //OVERDUE RENTALS

        addRental(new Rental(
                "978-7890123456",
                userId4,
                LocalDate.now().minusDays(40),
                LocalDate.now().minusDays(10)
        ));

        addRental(new Rental(
                "978-8901234567",
                userId4,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(5)
        ));

        addRental(new Rental(
                "978-9012345678",
                userId3,
                LocalDate.now().minusDays(45),
                LocalDate.now().minusDays(15)
        ));
    }
  
}
