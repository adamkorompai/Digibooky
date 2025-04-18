package com.switchfully.digibooky.api.dtos;

import java.util.Objects;

public class AuthorDto {
    private String firstname;
    private String lastname;

    public AuthorDto() {}

    public AuthorDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(firstname, authorDto.firstname) && Objects.equals(lastname, authorDto.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
