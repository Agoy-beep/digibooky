package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.*;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.domain.repositories.LentRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BookDetailsWithLentInfoService {
    BookDto bookDto;
    BookLentDto bookLentDto;
    MemberDto memberDto;
    BookRepository bookRepository;
    LentRepository lentRepository;
    MemberRepository memberRepository;
    BookMapper bookMapper = new BookMapper();
    LendMapper lendMapper = new LendMapper();
    MemberMapper memberMapper = new MemberMapper();

    @Autowired
    public BookDetailsWithLentInfoService(BookRepository bookRepository, LentRepository lentRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.lentRepository = lentRepository;
        this.memberRepository = memberRepository;
    }


    public BookDetailsWithLentInfoDto fillInAllDtosByBookID(String bookID) {
        BookDetailsWithLentInfoDto bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(null,null,null);
        if (StringUtils.isEmpty(bookID)) return bookDetailsWithLentInfoDto  ;
        Book aBook = bookRepository.getById(bookID);
        if (aBook == null) return bookDetailsWithLentInfoDto ;
        bookDto = bookMapper.mapToDto(aBook);
        bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto,null,null);
        if (lentRepository.isBookIDInRepository(aBook.getId())) {
            BookLent aBookLent = lentRepository.getLentByBookID(aBook.getId());
            bookLentDto = lendMapper.mapToDto(aBookLent);
            bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto,bookLentDto,null);
            if (memberRepository.isMemberIDInRepository(aBookLent.getLendeeID())) {
                Member aMember = memberRepository.getMemberByID(aBookLent.getLendeeID());
                memberDto = memberMapper.convertMemberToDto(aMember);
                bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto,bookLentDto,memberDto);
            }
        }
        return bookDetailsWithLentInfoDto;
    }
}