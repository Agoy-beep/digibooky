package com.testingtigers.domain.users;

import java.util.UUID;

public class Librarian implements Authenticatable {

    private final static Role ROLE = Role.LIBRARIAN;
    private final static String DEFAULT_PASSWORD = "admin";
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;

    public Librarian(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }
}
