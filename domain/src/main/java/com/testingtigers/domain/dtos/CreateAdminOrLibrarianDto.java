package com.testingtigers.domain.dtos;

public class CreateAdminOrLibrarianDto {
    private String firstName;
    private String lastName;
    private String emailAddress;

    public CreateAdminOrLibrarianDto(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return emailAddress;
    }

    public void setEmail(String email) {
        this.emailAddress = email;
    }
}
