package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.service.BookService;
import com.switchfully.digibooky.service.MemberService;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    private static final Logger log = LoggerFactory.getLogger(MemberControllerTest.class);

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
                "Springfield",
                "usernamelisa",
                "passwordlisa"
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
    public void RentABook_AddsARent() {

        CreateRentalDto dto = new CreateRentalDto();


        Response response = given()
                .port(port)
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("lisa1:passwordlisa")) // Add Authorization header
                .when()
                .get();





    }

    private String encodeBase64(String credentials) {
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
