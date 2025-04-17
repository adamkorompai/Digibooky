package com.switchfully.digibooky.domain;

public class Author {
    private long id;
    private String firstname;
    private String lastname;

    private static long nextId = 1;

    public Author(String firstname, String lastname) {
        this.id = nextId++;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
