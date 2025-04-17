package com.switchfully.digibooky.domain;

public enum Role {
    ADMIN,LIBRARIAN,MEMBER;

    @Override
    public String toString() {
        return this.name();
    }
}
