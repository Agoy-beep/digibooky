package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class LentRepository {

    private HashMap<String, BookLent> databaseLents; // do not make final
    private final LendMapper lendMapper = new LendMapper();

    @Autowired
    public LentRepository() {

        databaseLents = new HashMap<String, BookLent>();

    }

    //@Bean
    public List<BookLent> getAllLentsAsList() {
        return new ArrayList<BookLent>(databaseLents.values());
    }

    public boolean isBookIDInRepository(String bookId) {
        if (StringUtils.isEmpty(bookId)) return false;
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
        if ((bookToAdd == null) || (memberToAdd==null) || (startDate==null)) return null;
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
        if (StringUtils.isEmpty(memberID)) return null;
        List<BookLentDto> result = new ArrayList<BookLentDto>();

        for (BookLent bookLent : databaseLents.values()) {
            if (bookLent.getLendeeID().equals(memberID)) {
                result.add(lendMapper.convertBookLentToDto(bookLent));
            }
        }

        return result;
    }

    public List<BookLentDto> getAllBookLentsOverdue(Date dateToCheck) {
        if (dateToCheck == null) return null;
        List<BookLentDto> result = new ArrayList<>();
        for (BookLent bookLent : databaseLents.values()) {
            if (dateToCheck.after(bookLent.getLentEndDate())) {
                result.add(lendMapper.convertBookLentToDto(bookLent));
            }
        }
        return result;
    }

    public BookLentDto getLentDtoByBookID(String bookID) {
        if (StringUtils.isEmpty(bookID)) return null;

        for (BookLentDto bookLentDto : getAllLentsAsListDto()) {
            if (bookLentDto.getBookID().equals( bookID)) {
                return bookLentDto;
            }
        }
        return null;
    }
    public BookLent getLentByBookID(String bookID) {
        if ( StringUtils.isEmpty(bookID)) return null;

        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getBookID().equals( bookID)) {
                return bookLent;
            }
        }
        return null;
    }


    public void deleteLentByLentID(String lentID,Date returnDate) {
        if (StringUtils.isEmpty( lentID)) return;




        HashMap<String, BookLent> databaseLentsCopy = new HashMap<String, BookLent>();
        // Getting an iterator







    }
}



