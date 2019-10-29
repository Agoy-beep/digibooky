package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookRentalDto;
import com.testingtigers.domain.dtos.RentalMapper;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class RentalRepository {

    private HashMap<String, BookLent> databaseLents; // do not make final
    private final RentalMapper rentalMapper = new RentalMapper();

    @Autowired
    public RentalRepository() {

        databaseLents = new HashMap<String, BookLent>();

    }

    //@Bean
    public List<BookLent> getAllLentsAsList() {
        return new ArrayList<BookLent>(databaseLents.values());
    }

    public boolean isBookIDInRepository(String bookID) {
        if (StringUtils.isEmpty(bookID)) return false;
        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getBookID().equals(bookID)) return true;
        }
        return false;
    }

    public boolean isLentIDInRepository(String lentID) {
        if (StringUtils.isEmpty(lentID)) return  false;
        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getLentID().equals(lentID)) return true;
        }
        return false;
    }
    public List<BookRentalDto> getAllLentsAsListDto() {
        List<BookRentalDto> result = new ArrayList<BookRentalDto>();

        for (BookLent bookLent : databaseLents.values()) {
            result.add(rentalMapper.convertBookLentToDto(bookLent));

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

    public List<BookRentalDto> getLentBooksByMember(String memberID) {
        if (StringUtils.isEmpty(memberID)) return null;
        List<BookRentalDto> result = new ArrayList<BookRentalDto>();

        for (BookLent bookLent : databaseLents.values()) {
            if (bookLent.getLendeeID().equals(memberID)) {
                result.add(rentalMapper.convertBookLentToDto(bookLent));
            }
        }

        return result;
    }

    public List<BookRentalDto> getAllBookLentsOverdue(Date dateToCheck) {
        if (dateToCheck == null) return null;
        List<BookRentalDto> result = new ArrayList<>();
        for (BookLent bookLent : databaseLents.values()) {
            if (dateToCheck.after(bookLent.getLentEndDate())) {
                result.add(rentalMapper.convertBookLentToDto(bookLent));
            }
        }
        return result;
    }

    public BookRentalDto getLentDtoByLentID(String lentID) {
        if (StringUtils.isEmpty(lentID)) return null;

        for (BookRentalDto bookRentalDto : getAllLentsAsListDto()) {
            if (bookRentalDto.getLentID().equals( lentID)) {
                return bookRentalDto;
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


    public void deleteLentByLentID(String lentID) {
        if (StringUtils.isEmpty( lentID)) return;
        HashMap<String, BookLent> databaseLentsCopy = new HashMap<String, BookLent>();

        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getLentID().equals(lentID)) continue;
            databaseLentsCopy.put(bookLent.getLentID(), bookLent);
        }
        databaseLents = databaseLentsCopy;
    }
}



