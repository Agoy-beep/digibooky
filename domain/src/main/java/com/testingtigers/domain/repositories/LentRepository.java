package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LentRepository {

    private final HashMap<String, BookLent> databaseLents;
    private final LendMapper lendMapper;

    @Autowired
    public LentRepository(LendMapper lendMapper) {
        databaseLents = new HashMap<String, BookLent>();
        this.lendMapper = lendMapper;
    }

    //@Bean
    public List<BookLent> getAllLentsAsList() {
        return new ArrayList<BookLent>(databaseLents.values());
    }

    public boolean isBookIDInRepository(String bookId) {
        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getBookID().equals(bookId)) return true;
        }
        return false;
    }

    public List<BookLentDto> getAllLentsAsListDto() {
        List<BookLentDto> result = new ArrayList<BookLentDto>();

        for (BookLent bookLent : databaseLents.values()) {
            result.add(lendMapper.convertBookLentToDto(bookLent));

        }

        return result;
    }

    //@Bean
    public BookLent addBookToLent(Book bookToAdd, Member memberToAdd, Date startDate) {
        Date endDate;
        Calendar myCalender = Calendar.getInstance();
        myCalender.setTime(startDate);
        myCalender.add(Calendar.DAY_OF_MONTH, 3 * 7);
        endDate = myCalender.getTime();

        BookLent bookLentToAdd = new BookLent(bookToAdd.getId(), memberToAdd.getId(), startDate, endDate);
        databaseLents.put(bookLentToAdd.getLentID(), bookLentToAdd);
        return bookLentToAdd;
    }

    public List<BookLentDto> getLentBooksByMember(String memberID) {
        List<BookLentDto> result = new ArrayList<BookLentDto>();

        for (BookLent bookLent : databaseLents.values()) {
            if (bookLent.getLendeeID().equals(memberID)) {
                result.add(lendMapper.convertBookLentToDto(bookLent));
            }
        }

        return result;
    }

    public List<BookLentDto> getAllBookLentsOverdue(Date dateToCheck) {
        List<BookLentDto> result = new ArrayList<>();
        for (BookLent bookLent : databaseLents.values()) {
            if (dateToCheck.after(bookLent.getLentEndDate())) {
                result.add(lendMapper.convertBookLentToDto(bookLent));
            }
        }
        return result;
    }
}



