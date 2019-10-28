package com.testingtigers.service;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.repositories.AuthorRepository;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.domain.repositories.LentRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LentService {
    private final LentRepository lentRepository;
    private final MemberRepository memberRepository;
    private BookRepository bookRepository ;

    @Autowired
    public LentService(LentRepository lentRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.lentRepository = lentRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }


    public BookLent addBookToLent(String bookID, String lenderID, Date startDateToLent) {
        if ((bookID == null ) || (lenderID == null) || startDateToLent == null)
            throw new IllegalArgumentException("Member or book or date is null. Please retry with both values");
        if (bookRepository.getById(bookID)==null) return null; // exception is catched in bookrepository
        if (memberRepository.getMemberByID(lenderID) == null) return null; // exception is catched memberrepository
        return lentRepository.
                addBookToLent(bookRepository.getById(bookID),
                        memberRepository.getMemberByID(lenderID),
                        startDateToLent);
    }

    @Bean
    public List<BookLent> getAllLentBooks() {
        return lentRepository.getAllLentsAsList();
    }



}