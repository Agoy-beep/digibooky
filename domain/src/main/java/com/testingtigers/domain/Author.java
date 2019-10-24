package com.testingtigers.domain;

import java.util.UUID;

public class Author {

    private final String firstName;
    private final String lastName;
    private final String authorID;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorID = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAuthorID() {
        return authorID;
    }
}
