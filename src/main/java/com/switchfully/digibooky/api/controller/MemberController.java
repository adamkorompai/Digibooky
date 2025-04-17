package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.service.MemberService;
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

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
}
