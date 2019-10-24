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
        this.lastName = lastName;
        this.city = city;
        if (isEmailValid(emailAdress)) {
            this.emailAdress = emailAdress;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public String getINSS() {
        return INSS;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public static boolean isEmailValid(String email) {
        String emailCheckingRegex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(emailCheckingRegex);
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
