package com.testingtigers.domain.users;

import java.util.UUID;


public class Member {

    private String INSS;
    private final String id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String city;
    private String postalCode;
    private String streetName;
    private String streetNumber;

    public String getId() {
        return id;
    }

    public Member(String INSS, String emailAddress, String lastName, String city) {
        id = UUID.randomUUID().toString();
        if (INSS == null || emailAddress == null || lastName == null || city == null) {
            throw new IllegalArgumentException("Please fill in all fields");
        }
        this.INSS = INSS;
        this.lastName = lastName;
        this.city = city;
        if (isEmailValid(emailAddress)) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public String getINSS() {
        return INSS;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setINSS(String INSS) {
        this.INSS = INSS;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
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
