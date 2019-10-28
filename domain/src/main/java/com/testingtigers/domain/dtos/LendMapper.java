package com.testingtigers.domain.dtos;

import com.testingtigers.domain.BookLent;

public class LendMapper {

    public BookLentDto convertBookLentToDto(BookLent bookLent) {
        BookLentDto bookLentDto = new BookLentDto();
        bookLentDto.setBookID(bookLent.getBookID());
        bookLentDto.setLentStartDate(bookLent.getLentStartDate());
        bookLentDto.setLendeeID(bookLent.getLendeeID());
        bookLentDto.setLentID(bookLent.getLentID());
        bookLentDto.setLentEndDate(bookLent.getLentEndDate());
        return bookLentDto;
    }

    public BookLent mapToBookLent(BookLentDto bookLentDto) {
        return new BookLent(bookLentDto.getBookID(), bookLentDto.getLendeeID(), bookLentDto.getLentStartDate(), bookLentDto.getLentEndDate());
    }
}
