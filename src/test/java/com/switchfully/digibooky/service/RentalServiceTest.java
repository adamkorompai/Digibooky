package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.BookDetailsMemberDto;
import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.api.dtos.mapper.BookMapper;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.repository.MemberRepository;
import com.switchfully.digibooky.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() {
        rentalRepository = new RentalRepository();
        memberMapper = new MemberMapper();
        memberRepository = new MemberRepository(memberMapper);
        bookRepository = new BookRepository();
        bookMapper = new BookMapper();
        rentalMapper = new RentalMapper();
        bookService = new BookService(bookRepository, bookMapper, memberMapper);
        rentalService = new RentalService(rentalRepository, rentalMapper,memberRepository,bookRepository, rentalRepository, bookRepository,bookService, bookMapper);

    }


//      INITIALIZED VALUE
//    String userId1 = "user-123";
//    String userId2 = "user-456";
//    String userId3 = "user-789";
//    String userId4 = "user-101";
//
//    addRental(new Rental(
//                      "978-1234567890",
//              userId1,
//              LocalDate.now().minusDays(10),
//                LocalDate.now().plusDays(11)
//        ));
//
//    addRental(new Rental(
//                      "978-2345678901",
//              userId1,
//              LocalDate.now().minusDays(5),
//                LocalDate.now().plusDays(16)
//        ));
//
//    addRental(new Rental(
//                      "978-3456789012",
//              userId2,
//              LocalDate.now().minusDays(15),
//                LocalDate.now().plusDays(6)
//        ));
//
//    addRental(new Rental(
//                      "978-4567890123",
//              userId3,
//              LocalDate.now().minusDays(20),
//                LocalDate.now().plusDays(1)
//        ));
//
//    addRental(new Rental(
//                      "978-5678901234",
//              userId3,
//              LocalDate.now().minusDays(18),
//                LocalDate.now().plusDays(3)
//        ));
//
//    addRental(new Rental(
//                      "978-6789012345",
//              userId3,
//              LocalDate.now().minusDays(7),
//                LocalDate.now().plusDays(14)
//        ));
//
//    //OVERDUE RENTALS
//
//    addRental(new Rental(
//                      "978-7890123456",
//              userId4,
//              LocalDate.now().minusDays(40),
//                LocalDate.now().minusDays(10)
//        ));
//
//    addRental(new Rental(
//                      "978-8901234567",
//              userId4,
//              LocalDate.now().minusDays(35),
//                LocalDate.now().minusDays(5)
//        ));
//
//    addRental(new Rental(
//                      "978-9012345678",
//              userId3,
//              LocalDate.now().minusDays(45),
//                LocalDate.now().minusDays(15)
//        ));

    @Test
    public void testGetRentalsByUserId_ReturnsCorrectRentals() {
        String userId = "user-123";

        List<RentalDto> result = rentalService.getRentalsByUserId(userId);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetRentalsByUserId_NoRentals_ReturnsEmptyList() {

        String userId = "user456";

        List<RentalDto> result = rentalService.getRentalsByUserId(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetOverdueRentals_ReturnsOverdueRentals() {
        List<RentalDto> result = rentalService.getOverdueRentals();

        assertEquals(3, result.size());
    }

//    @Test
//    public void testGetBookDetailsForMemberById_BookWithActiveRental() {
//        BookDetailsMemberDto result = rentalService.getBookDetailsForMemberById(1);
//        Author author = new Author("Jane", "Doe");
//
//        assertEquals("The Great Adventure", result.getTitle());
//        assertEquals(author , result.getAuthor());
//        assertEquals("978-1-23-456789-7", result.getIsbn());
//        assertFalse(result.isBorrowed());
//        assertEquals(null, result.getBorrowedByUserId());
//    }
}
