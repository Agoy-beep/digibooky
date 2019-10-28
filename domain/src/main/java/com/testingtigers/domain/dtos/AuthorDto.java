package com.testingtigers.domain.dtos;

import java.util.UUID;

public class AuthorDto {

    private String firstName;
    private String lastName;
    private String authorID;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAuthorId(String authorId) {this.authorID = authorId; }
}
