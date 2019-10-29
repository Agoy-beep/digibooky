package com.testingtigers.domain.dtos;

import com.testingtigers.domain.BookLent;

public class RentalMapper {

    public BookRentalDto convertBookLentToDto(BookLent bookLent) {
        BookRentalDto bookRentalDto = new BookRentalDto();
        bookRentalDto.setBookID(bookLent.getBookID());
        bookRentalDto.setLentStartDate(bookLent.getLentStartDate());
        bookRentalDto.setLendeeID(bookLent.getLendeeID());
        bookRentalDto.setLentID(bookLent.getLentID());
        bookRentalDto.setLentEndDate(bookLent.getLentEndDate());
        return bookRentalDto;
    }

    public BookLent mapToBookLent(BookRentalDto bookRentalDto) {
        return new BookLent(bookRentalDto.getBookID(), bookRentalDto.getLendeeID(), bookRentalDto.getLentStartDate(), bookRentalDto.getLentEndDate());
    }
    public BookRentalDto mapToDto(BookLent bookLent) {
        BookRentalDto bookRentalDto = new BookRentalDto();
        bookRentalDto.setBookID(bookLent.getBookID());
        bookRentalDto.setLentStartDate(bookLent.getLentStartDate());
        bookRentalDto.setLendeeID(bookLent.getLendeeID());
        bookRentalDto.setLentID(bookLent.getLentID());
        bookRentalDto.setLentEndDate(bookLent.getLentEndDate());
        return bookRentalDto;
    }
}
