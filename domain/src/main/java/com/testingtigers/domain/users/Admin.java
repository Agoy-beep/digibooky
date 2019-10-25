package com.testingtigers.domain.users;

import java.util.UUID;

public class Admin {

    private String id;
    private String lastName;
    private String firstName;
    private String email;

    public Admin(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        if (Member.isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
        this.id = UUID.randomUUID().toString();
    }
}
