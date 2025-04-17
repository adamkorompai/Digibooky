package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Role;

public class CreateMemberDto {

    private String id;//Is required and unique
    private String INSS;//Is required and unique
    private String lastName;//Is required
    private String firstName;
    private String email;//Is required and form validation
    private String streetName;
    private String streetNumber;
    private String city;//Is required

    private Role role;

    public CreateMemberDto( String INSS, String lastName, String email, String city) {
        this.INSS = INSS;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.role = Role.MEMBER;
    }

    public CreateMemberDto(String INSS, String lastName, String firstName, String email, String streetName, String streetNumber, String city, Role role) {
        this.INSS = INSS;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.role = Role.MEMBER;
    }

    public String getINSS() {
        return INSS;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public Role getRole() {
        return role;
    }
}
