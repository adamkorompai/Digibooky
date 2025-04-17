package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
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
    public MemberDto saveMember(@RequestBody String inss,@RequestBody MemberDto memberDto) {
        log.info("Inside saveMember" + memberDto);

        return memberService.saveMember(inss,memberDto);
    }
}
