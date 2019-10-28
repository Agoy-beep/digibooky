package com.testingtigers.domain.dtos;

import java.util.Date;
import java.util.UUID;

public class BookLentDto {
    String bookID;
    String lendeeID;
    Date lentDate;

    public String getBookID() {
        return bookID;
    }

    public String getLendeeID() {
        return lendeeID;
    }

    public Date getLentDate() {
        return lentDate;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setLendeeID(String lendeeID) {
        this.lendeeID = lendeeID;
    }

    public void setLentDate(Date lentDate) {
        this.lentDate = lentDate;
    }

    public void setLentID(String lentID) {
        this.lentID = lentID;
    }

    String lentID;

    public String getLentID() {
        return lentID;
    }

}
