package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class LentRepository {

    private final HashMap<String,BookLent> databaseLents;
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
        for (BookLent bookLent : getAllLentsAsList()) {
            if (bookLent.getBookID().equals(bookId)) return true;
        }
        return  false;
    }


    public List<BookLentDto> getAllLentsAsListDto() {
        List<BookLentDto> result = new ArrayList<BookLentDto>();

        for(BookLent bookLent : databaseLents.values()) {
            //TODO is this for debugging purposes?
            System.out.println(" XXXXXXXXXXXXXXXXXXXXXXXXX in getAllLentsAsListDto");
            result.add( lendMapper.convertBookLentToDto(bookLent));
        }

        return result;
    }

    //@Bean
    public BookLent addBookToLent(Book bookToAdd, Member memberToAdd, Date startDate) {
        BookLent bookLentToAdd = new BookLent(bookToAdd.getId(), memberToAdd.getId(), startDate);
        databaseLents.put(bookLentToAdd.getLentID(),bookLentToAdd);
        return bookLentToAdd;
    }

}



