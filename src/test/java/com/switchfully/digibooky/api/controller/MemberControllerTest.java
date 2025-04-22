package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Role;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.service.BookService;
import com.switchfully.digibooky.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {


    @LocalServerPort
    private int port;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    /*
    *
    * Member member1 = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com");
        Member member2 = Member.createLibrarian("Gandalf","leblanc","gadalf_leblan@hotmail.com");
        Member member3 = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);

    *
    * */

    @Test
    public void SavingMember_ShouldSaveMember() {
        CreateMemberDto dto = new CreateMemberDto(
                "951014-523-14",
                "Lisa",
                "lisax_simpson@hotmail.com",
                "Springfield"
        );

        given()
                .port(port)
                .contentType("application/json")
                .when()
                .body(dto)
                .post("/digibooky/members")
                .then()
                .statusCode(201)
                .equals(dto);

    }

    @Test
    public void getAllMembers_ShouldGiveAllMembers() {
        int size = memberService.getAllMember().size();

        given()
                .port(port)
                .contentType("application/json")
                .when()
                .get("/digibooky/members")
                .then()
                .statusCode(200)
                .body("size()", equalTo(size));

    }

    @Test
    public void createAdmin_AddsAdmin() {
        int size = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.ADMIN)).toList().size();

        MemberDto dto = new MemberDto(
                "951014-523-14",
                "Lisa",
                "lisax_simpson@hotmail.com"
        );
        memberService.createAdmin(dto);

        int newSize = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.ADMIN)).toList().size();


        given()
                .port(port)
                .contentType("application/json")
                .when()
                .body(dto)
                .post("/digibooky/members/admin")
                .then()
                .statusCode(201);

        Assertions.assertEquals(newSize, size + 1);
    }


}
