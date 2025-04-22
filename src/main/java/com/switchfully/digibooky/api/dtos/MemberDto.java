package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Role;

import java.util.UUID;

public class MemberDto {


    private String lastName;
    private String firstName;
    private String email;
    private String streetName;
    private String streetNumber;
    private String city;
    private String username;
    private String password;

    private Role role;
    public MemberDto(){
        // JACKSON
    }

    public MemberDto(String lastName, String firstName, String email, String username, String password){
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public MemberDto( String lastName, String email, String city, Role role, String username, String password) {
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public MemberDto(String lastName, String firstName, String email, String streetName, String streetNumber, String city, Role role, String username, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.role = role;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", city='" + city + '\'' +
                ", role=" + role +
                '}';
    }
}
