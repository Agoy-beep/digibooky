package com.testingtigers.domain;

import org.apache.groovy.json.internal.Dates;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class BookLent {
    final String bookID;
    final String lendeeID;
    final Date lentStartDate;

    final Date lentEndDate;
    final String lentID;

    public String getBookID() {
        return bookID;
    }

    public String getLendeeID() {
        return lendeeID;
    }

    public Date getLentStartDate() {
        return lentStartDate;
    }

    public String getLentID() {
        return lentID;
    }

    public Date getLentEndDate() {
        return lentEndDate;
    }

    public BookLent(String bookID, String lendeeID, Date lentStartDate, Date lentEndDate) {
        this.bookID = bookID;
        this.lendeeID = lendeeID;
        this.lentStartDate = lentStartDate;
        this.lentEndDate = lentEndDate;

        lentID = UUID.randomUUID().toString();
    }
}
