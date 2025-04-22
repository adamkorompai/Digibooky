package com.switchfully.digibooky.service;


import com.switchfully.digibooky.api.dtos.BookDetailsMemberDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.exceptions.ResourcenNotFoundException;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.repository.MemberRepository;
import com.switchfully.digibooky.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentals;
    private final RentalMapper rentalMapper;
    private final MemberRepository members;
    private final BookRepository books;
    private final RentalRepository rentalRepository;

    private static final int MAX_WEEK_RETURN = 3;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public RentalService(RentalRepository rentals, RentalMapper rentalMapper, MemberRepository members, BookRepository books, RentalRepository rentalRepository, BookRepository bookRepository, BookService bookService, BookMapper bookMapper) {
        this.rentals = rentals;
        this.rentalMapper = rentalMapper;
        this.members = members;
        this.books = books;
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }




    //TODO : Validation du ISBN
    //TODO:
    public CreateRentalDto createRent(CreateRentalDto rentalDto) {
        Book book = books.getBookByIsbn(rentalDto.getBookIsbn());
        Member member = members.getMember(rentalDto.getUserId());

        if (book.getNumberOfCopy() == 0) {
            throw new ResourcenNotFoundException("No copy available");
        } else if (book.getNumberOfCopy() == -1 ) {
            throw new ResourcenNotFoundException("This book is deleted");
        }

        bookRepository.removeCopyOfBook(book.getId());
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

    public List<RentalDto> getRentalsByUserId(String userId) {
        List<Rental> rentals = rentalRepository.getRentalsByUserId(userId);
        return rentals.stream().map(rentalMapper::map).collect(Collectors.toList());
    }

    public List<RentalDto> getOverdueRentals() {
        List<Rental> rentals = rentalRepository.getOverdueRentals();
        return rentals.stream().map(rentalMapper::map).collect(Collectors.toList());
    }

    public BookDetailsMemberDto getBookDetailsForMemberById(long id) {
        BookDto book = bookService.getBookById(id);

        Rental rental = rentalRepository.getActiveRentalByIsbn(book.getIsbn());

        return bookMapper.mapToBookDetailsMemberDto(book, rental);
    }
}
