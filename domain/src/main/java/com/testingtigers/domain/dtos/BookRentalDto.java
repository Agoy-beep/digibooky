package com.testingtigers.domain.dtos;

import java.util.Date;

public class BookRentalDto {
    String bookID;
    String lendeeID;
    Date lentStartDate;
    Date lentEndDate;
    String lentID;

    @Override
    public String toString() {
        return  "bookID='" + bookID + '\'' +
                ", lendeeID='" + lendeeID + '\'' +
                ", lentStartDate=" + lentStartDate +
                ", lentEndDate=" + lentEndDate +
                ", lentID='" + lentID + '\'';

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

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setLendeeID(String lendeeID) {
        this.lendeeID = lendeeID;
    }

    public void setLentStartDate(Date lentStartDate) {
        this.lentStartDate = lentStartDate;
    }

    public void setLentID(String lentID) {
        this.lentID = lentID;
    }

    public String getLentID() {
        return lentID;
    }

    public void setLentEndDate(Date lentEndDate) {
        this.lentEndDate = lentEndDate;
    }

    public Date getLentEndDate() {
        return lentEndDate;
    }
}
