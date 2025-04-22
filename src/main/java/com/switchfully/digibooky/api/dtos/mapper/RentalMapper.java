package com.switchfully.digibooky.api.dtos.mapper;

import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.domain.Rental;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentalMapper {

    public Rental map(CreateRentalDto rental, LocalDate rentalDate) {
        return new Rental(
                rental.getBookIsbn(),
                rental.getUserId(),
                rental.getRentDate(),
                rentalDate
        );
    }

    public RentalDto map(Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getBookIsbn(),
                rental.getUserId(),
                rental.getRentalDate(),
                rental.getDueDate()
        );
    }


}
