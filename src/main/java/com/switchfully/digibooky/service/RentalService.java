package com.switchfully.digibooky.service;


import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.repository.MemberRepository;
import com.switchfully.digibooky.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RentalService {

    private final RentalRepository rentals;
    private final RentalMapper rentalMapper;
    private final MemberRepository members;
    private final BookRepository books;
    private final RentalRepository rentalRepository;

    private static final int MAX_WEEK_RETURN = 3;

    @Autowired
    public RentalService(RentalRepository rentals, RentalMapper rentalMapper, MemberRepository members, BookRepository books, RentalRepository rentalRepository) {
        this.rentals = rentals;
        this.rentalMapper = rentalMapper;
        this.members = members;
        this.books = books;
        this.rentalRepository = rentalRepository;
    }




    //TODO : Validation du ISBN
    //TODO:
    public CreateRentalDto createRent(CreateRentalDto rentalDto) {
        Book book = books.getBookByIsbn(rentalDto.getBookIsbn());
        Member member = members.getMember(rentalDto.getUserId());

        //TODO: Book Only Once A time

        Rental rental = rentalMapper.map(rentalDto,getReturnDate(rentalDto.getRentDate()));

        rental = rentalRepository.addRental(rental);
        return rentalDto;

    }

    public RentalDto returnRent(String rentalId) {
        Rental rental = rentalRepository.getRental(rentalId);
        rental.returnRental();
        rental = rentalRepository.addRental(rental);

        //C'est bizarre de throw un truc parce que c'est late, Que dois je faire ?
        if(rental.isLateRental())
            throw new IllegalArgumentException("Late rental");
        return rentalMapper.map(rental);
    }

    public LocalDate getReturnDate(LocalDate date) {
        return date.plusWeeks(MAX_WEEK_RETURN);
    }
}
