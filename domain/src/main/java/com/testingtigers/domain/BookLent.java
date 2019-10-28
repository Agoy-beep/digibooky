package com.testingtigers.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

public class BookLent {
    final String bookID;
    final String lendeeID;
    final Date lentDate;
    final String lentID;


    public String getLentID() {
        return lentID;
    }


    public BookLent(String bookID, String lendeeID, Date lentDate) {
        this.bookID = bookID;
        this.lendeeID = lendeeID;
        this.lentDate = lentDate;
        lentID = UUID.randomUUID().toString();
    }
}
