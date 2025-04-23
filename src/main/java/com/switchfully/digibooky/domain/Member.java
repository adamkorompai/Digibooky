package com.switchfully.digibooky.domain;

import java.util.UUID;

public class Member {

    private String id;//Is required and unique
    private String INSS;//Is required and unique
    private String lastName;//Is required
    private String firstName;
    private String email;//Is required and form validation
    private String streetName;
    private String streetNumber;
    private String city;//Is required
    private String username;//Is required
    private String password; //Is required

    private Role role;

    public Member(String lastName, String firstName, String email, Role role, String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Member(String INSS, String lastName, String firstName, String email, Role role, String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.INSS = INSS;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Member(String INSS, String lastName, String firstName, String email, String streetName, String streetNumber, String city, Role role, String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.INSS = INSS;

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

    public static Member createAdmin(String lastName, String firstName, String email, String username, String password) {
        return new Member(
                lastName,
                firstName,
                email,
                Role.ADMIN,
                username,
                password
        );
    }

    public static Member createLibrarian(String lastName, String firstName, String email, String username, String password) {
        return new Member(
                lastName,
                firstName,
                email,
                Role.LIBRARIAN,
                username,
                password
        );
    }


    public String getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", INSS='" + INSS + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", city='" + city + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
