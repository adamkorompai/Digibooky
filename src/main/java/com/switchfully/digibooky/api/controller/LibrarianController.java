package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.service.RentalService;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/digibooky/librarian")
public class LibrarianController {

    private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);
    private final BookService bookService;
    private final RentalService rentalService;

    @Autowired
    public LibrarianController(BookService bookService, RentalService rentalService) {
        this.bookService = bookService;
        this.rentalService = rentalService;
    }

    @PostMapping(value = "/books",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping(path="/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        return bookService.updateBook(isbn, bookDto);
    }

    @DeleteMapping(path="/books/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);

    }

    @GetMapping("/rentals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getRentalsByUserId(@PathVariable("id") String userId) {
         return rentalService.getRentalsByUserId(userId);
    }

    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getOverdueRentals() {
        return rentalService.getOverdueRentals();
    }
}
