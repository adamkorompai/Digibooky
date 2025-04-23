package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.BookDetailsMemberDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.domain.*;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.repository.MemberRepository;
import com.switchfully.digibooky.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RentalServiceTest {
    private RentalService rentalService;
    private RentalRepository rentalRepository;
    private MemberRepository memberRepository;
    private BookService bookService;
    private RentalMapper rentalMapper;
    private BookMapper bookMapper;
    private MemberMapper memberMapper;
    private BookRepository bookRepository;

    private Book testBook;
    private Member testMember;
    private Rental testRental;
    private String testBookIsbn;
    private CreateRentalDto testCreateRentalDto;
    private Rental overdueRental;

    @BeforeEach
    void setUp() {
        memberMapper = new MemberMapper();
        memberRepository = new MemberRepository(memberMapper);
        bookRepository = new BookRepository();
        bookMapper = new BookMapper();
        rentalMapper = new RentalMapper();
        bookService = new BookService(bookRepository, bookMapper, memberMapper);
        rentalRepository = new RentalRepository();
        rentalService = new RentalService(rentalRepository, rentalMapper,memberRepository,bookRepository, rentalRepository, bookService, bookMapper);

        testBookIsbn = "9781234567890";
        Author testAuthor = new Author("John", "Doe");

        testBook = new Book(testBookIsbn, testAuthor,"Test title", "a little story", 1);
        bookRepository.saveBook(testBook);

        testMember = new Member("kor", "adam", "a@g.com", Role.MEMBER, "adama", "password");
        memberRepository.save(testMember);

        testCreateRentalDto = new CreateRentalDto();
        testCreateRentalDto.setUserId(testMember.getId());
        testCreateRentalDto.setBookIsbn(testBookIsbn);
        testCreateRentalDto.setRentDate(LocalDate.now());

        testRental = new Rental(testBookIsbn, testMember.getId(), LocalDate.now(), LocalDate.now().plusWeeks(3));

        LocalDate pastRentDate = LocalDate.now().minusMonths(1);
        LocalDate pastReturnDate = pastRentDate.plusWeeks(3);
        overdueRental = new Rental(testBookIsbn, testMember.getId(), pastRentDate, pastReturnDate);

    }

    @Test
    void createRent_WithAvailableBook_CreatesRental() {

        CreateRentalDto result = rentalService.createRent(testCreateRentalDto);

        assertEquals(testCreateRentalDto, result);
        assertEquals(0, testBook.getNumberOfCopy());

        List<Rental> userRentals = rentalRepository.getRentalsByUserId(testMember.getId());
        assertFalse(userRentals.isEmpty());
        assertEquals(testBookIsbn, userRentals.get(0).getBookIsbn());
    }

    @Test
    void getRentalsByUserId_ReturnsUserRentals() {
        rentalRepository.addRental(testRental);

        List<RentalDto> result = rentalService.getRentalsByUserId(testMember.getId());

        assertEquals(1, result.size());
        assertEquals(testRental.getId(), result.get(0).getId());
    }

    @Test
    void getOverdueRentals_ReturnsOverdueRentals() {
        rentalRepository.addRental(overdueRental);

        List<RentalDto> result = rentalService.getOverdueRentals();

        assertEquals(4, result.size()); // 3 overdue rentals are initialized in the repo
        assertEquals(overdueRental.getId(), result.get(3).getId());
    }

    @Test
    void getBookDetailsForMemberById_ReturnsBookDetails() {
        rentalRepository.addRental(testRental);

        BookDetailsMemberDto result = rentalService.getBookDetailsForMemberById(testBook.getId());

        assertNotNull(result);
        assertEquals(testBook.getIsbn(), result.getIsbn());
        assertEquals(testBook.getTitle(), result.getTitle());
        assertEquals(true, result.isBorrowed());
    }
}
