package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookBuilder;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.users.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class LendMapper {

    public BookLentDto convertBookLentToDto(BookLent bookLent) {
        BookLentDto bookLentDto = new BookLentDto();
        bookLentDto.setBookID(bookLent.getBookID());
        bookLentDto.setLentDate(bookLent.getLentDate());
        bookLentDto.setLendeeID(bookLent.getLendeeID());
        bookLentDto.setLentID(bookLent.getLentID());
        return bookLentDto;
    }

    public BookLent mapToBookLent(BookLentDto bookLentDto) {
        return new BookLent(bookLentDto.getBookID(),bookLentDto.getLendeeID(),bookLentDto.getLentDate());
    }
}
