package com.testingtigers.domain.dtos;

import groovy.transform.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

public class BookDetailsWithLentInfoDto {

    private String isbn;
    private String title;
    private String authorID;
    private String summary;

    private String bookID;
    private String lendeeID;
    private Date lentStartDate;
    private Date lentEndDate;
    private String lentID;

    private String INSS;
    private String emailAdress;
    private String firstName;
    private String lastName;
    private String city;
    private String postalCode;
    private String streetName;
    private String streetNumber;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getSummary() {
        return summary;
    }

    public String getBookID() {
        return bookID;
    }

    public String getLendeeID() {
        return lendeeID;
    }

    public Date getLentStartDate() {
        return lentStartDate;
    }

    public Date getLentEndDate() {
        return lentEndDate;
    }

    public String getLentID() {
        return lentID;
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

    public BookDetailsWithLentInfoDto(BookDto bookDto, BookLentDto bookLentDto, MemberDto memberDto) {



        if (memberDto != null) {
            INSS = memberDto.getINSS();
            emailAdress = memberDto.getEmailAdress();
            firstName = memberDto.getFirstName();
            lastName = memberDto.getLastName();
            city = memberDto.getCity();
            postalCode = memberDto.getPostalCode();
            streetName = memberDto.getStreetName();
            streetNumber = memberDto.getStreetNumber();
        }

        if (bookDto != null) {
            isbn = bookDto.getIsbn();
            title = bookDto.getTitle();
            authorID = bookDto.getAuthorID();
            summary = bookDto.getSummary();
        }

        if (bookLentDto != null) {
            bookID = bookLentDto.getBookID();
            lendeeID = bookLentDto.getLendeeID();
            lentStartDate = bookLentDto.getLentStartDate();
            lentEndDate = bookLentDto.getLentEndDate();
            lentID = bookLentDto.getLentID();
        }

    }

}
