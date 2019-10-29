package com.testingtigers.domain;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TicketAfterReturn {

    private final String startDate;
    private final String endDate;
    private final String daysOverdue;
    private final String bookID;
    private final String memberID;


    private final String lentID;

    public TicketAfterReturn(Date dateOfReturn,Date startDate, Date endDate, String bookID, String memberID,String lentID) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.lentID = lentID;
        Calendar myCalender = Calendar.getInstance();
        myCalender.setTime(dateOfReturn);
        if ( myCalender.before(endDate)) {
            Long diff = dateOfReturn.getTime() - endDate.getTime();
            daysOverdue = Long.toString(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS));
        }
        else
            daysOverdue = "0";
        this.startDate = startDate.toString();
        this.endDate = startDate.toString();
    }
    public String getLentID() {
        return lentID;
    }
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDaysOverdue() {
        return daysOverdue;
    }

    public String getBookID() {
        return bookID;
    }

    public String getMemberID() {
        return memberID;
    }
}

/*
public static long getDifferenceDays(Date d1, Date d2) {
    long diff = d2.getTime() - d1.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
}
 */
