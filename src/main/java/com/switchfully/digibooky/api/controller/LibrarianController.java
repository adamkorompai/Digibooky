package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/digibooky/librarian")
public class LibrarianController {

    private final RentalService rentalService;
    public LibrarianController(RentalService rentalService) {
        this.rentalService = rentalService;
    }


}
