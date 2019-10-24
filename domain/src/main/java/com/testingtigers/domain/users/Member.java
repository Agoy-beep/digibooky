package com.testingtigers.domain.users;

import java.util.UUID;

public class Member {

    private String INSS;
    private final String id;
    private String emailAdress;
    private String firstName;
    private String lastName;
    private String city;
    private String postalCode;
    private String streetName;
    private String streetNumber;

    public String getId() {
        return id;
    }

    public Member(String INSS, String emailAdress, String lastName, String city) {
        id = UUID.randomUUID().toString();
        this.INSS = INSS;
        this.emailAdress = emailAdress;
        this.lastName = lastName;
        this.city = city;
    }

    public Member setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Member setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public Member setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Member setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }
}
