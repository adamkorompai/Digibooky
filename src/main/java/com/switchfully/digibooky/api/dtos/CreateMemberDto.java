package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Role;

public class CreateMemberDto {

    private String inss;//Is required and unique
    private String lastName;//Is required
    private String firstName;
    private String email;//Is required and form validation
    private String streetName;
    private String streetNumber;
    private String city;//Is required

    private Role role = Role.MEMBER;

    CreateMemberDto() {
    }

    public CreateMemberDto(String inss, String lastName, String email, String city) {
        this.inss = inss;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.role = Role.MEMBER;
    }

    public CreateMemberDto(String inss, String lastName, String firstName, String email, String streetName, String streetNumber, String city, Role role) {
        this.inss = inss;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.role = Role.MEMBER;
    }

    public String getInss() {
        return inss;
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

    @Override
    public String toString() {
        return "CreateMemberDto{" +
                "INSS='" + inss + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", city='" + city + '\'' +
                ", role=" + role +
                '}';
    }
}
