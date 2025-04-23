package com.switchfully.digibooky.api.controller;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Role;
import com.switchfully.digibooky.service.MemberService;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MemberService memberService;

    @Test
    public void getAllMembers_ShouldGiveAllMembers() {
        int size = memberService.getAllMember().size();

        given()
                .port(port)
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("peter1:passwordpeter")) // Add Authorization header
                .when()
                .get("/digibooky/admin")
                .then()
                .statusCode(200)
                .body("size()", equalTo(size));

    }

    @Test
    public void createAdmin_AddsAdmin() {
        MemberDto dto = new MemberDto(
                "951014-523-14",
                "Lisa",
                "lisaxx_simpson@hotmail.com",
                "username200",
                "password"
        );

        given()
                .port(port)
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("peter1:passwordpeter")) // Add Authorization header
                .when()
                .body(dto)
                .post("/digibooky/admin")
                .then()
                .statusCode(201);

    }

    private String encodeBase64(String credentials) {
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}

