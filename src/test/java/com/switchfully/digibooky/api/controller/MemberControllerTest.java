package com.switchfully.digibooky.api.controller;


import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.CreateRentalDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.RentalDto;
import com.switchfully.digibooky.domain.Role;
import com.switchfully.digibooky.repository.BookRepository;
import com.switchfully.digibooky.service.BookService;
import com.switchfully.digibooky.service.MemberService;
import com.switchfully.digibooky.service.RentalService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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

    @Autowired
    private RentalService rentalService;

    private String base64Credentials;




    /*
    *
    * Member member1 = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com");
        Member member2 = Member.createLibrarian("Gandalf","leblanc","gadalf_leblan@hotmail.com");
        Member member3 = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);

    *
    * */


    @BeforeEach
    void setUp() {
        String credentials = "bob1:passwordbob";
        base64Credentials = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    @Test
    public void SavingMember_ShouldSaveMember() {
        CreateMemberDto dto = new CreateMemberDto(
                "951014-523-14",
                "Lisa",
                "lisax_simpson@hotmail.com",
                "Springfield",
                "username",
                "password"
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
                .header("Authorization",base64Credentials)
                .when()
                .get("/digibooky/admin")
                .then()
                .statusCode(200)
                .body("size()", equalTo(size));

    }

    @Test
    public void createAdmin_AddsAdmin() {
        int size = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.ADMIN)).toList().size();
        log.info(memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.ADMIN)).toList().toString());
        MemberDto dto = new MemberDto(
                "951014-523-14",
                "Lisa",
                "Charles_Xavier@hotmail.com",
                "username200",
                "password"
        );
        Response response = given()
                .port(port)
                .contentType("application/json")
                .header("Authorization",base64Credentials)
                .when()
                .body(dto)
                .post("/digibooky/admin");

        System.out.println(response.getBody().prettyPrint());



        int new_size = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.ADMIN)).toList().size();

        Assertions.assertEquals(new_size, size + 1);
    }

    @Test
    public void createLibrarian_AddsLibrarian() {
        int size = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.LIBRARIAN)).toList().size();

        MemberDto dto = new MemberDto(
                "Lisa",
                "Simpsons",
                "lisaxx_simpson@hotmail.com",
                "Lize",
                "simps"
        );
        given()
                .port(port)
                .contentType("application/json")
                .header("Authorization",base64Credentials)
                .when()
                .body(dto)
                .post("/digibooky/admin/librarian")
                .then()
                .statusCode(201);

        int new_size = memberService.getAllMember().stream().filter(e -> e.getRole().equals(Role.LIBRARIAN)).toList().size();

        Assertions.assertEquals(new_size, size + 1);
    }


    //Todo Not finished
    @Test
    public void RentABook_AddsARent() {

        CreateRentalDto dto = new CreateRentalDto();


        Response response = given()
                .port(port)
                .contentType("application/json")
                .header("Authorization",base64Credentials)
                .when()
                .get();





    }
}
