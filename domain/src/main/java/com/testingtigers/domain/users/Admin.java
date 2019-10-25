package com.testingtigers.domain.users;

import java.util.UUID;

public class Admin implements Authenticatable {

    private final static Role ROLE = Role.ADMIN;
    private final static String DEFAULT_PASSWORD = "admin";
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;

    public Admin(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        if (Member.isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
        this.id = UUID.randomUUID().toString();
        password = DEFAULT_PASSWORD;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Role getRole() {
        return ROLE;
    }
}
