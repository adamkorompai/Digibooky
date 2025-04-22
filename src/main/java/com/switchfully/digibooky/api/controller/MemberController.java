package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.service.MemberService;
import com.switchfully.digibooky.service.RentalService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/digibooky/members")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private MemberService memberService;


    private final RentalService rentalService;


    @Autowired
    public MemberController(MemberService memberService, RentalService rentalService) {
        this.memberService = memberService;
        this.rentalService = rentalService;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMemberDto saveMember(HttpServletRequest request, @RequestBody CreateMemberDto createMemberDto) {
        log.info("Inside saveMember" + createMemberDto);
        //System.out.println(Arrays.toString(request.getHeader("Authorization").replace("Basic ", "").split(":")));
        return memberService.saveMember(createMemberDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> getAllMember() {

        log.info("Inside getAllMember");
        return memberService.getAllMember();
    }

    //TODO : MemberDto => CreateMemberDto et check if it was an admin that has been sent
    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createAdmin(@RequestBody MemberDto memberDto) {
        log.info("Inside createAdmin" + memberDto);
        return memberService.createAdmin(memberDto);
    }

    @PostMapping(value = "/librarian", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createLibrarian(@RequestBody MemberDto memberDto) {
        log.info("Inside createLibrarian" + memberDto);
        return memberService.createLibrarian(memberDto);
    }

    // This is an Only member feature that's
    @PostMapping(value = "/rent",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalDto addRental(@RequestBody CreateRentalDto rental) {
        return rentalService.createRent(rental);
    }

    // This is an Only member feature that's
    @PostMapping(value = "/return/{rentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RentalDto returnBook(@PathVariable String rentId) {
        return rentalService.returnRent(rentId);
    }


}
