package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.*;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.domain.repositories.RentalRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BookDetailsWithRentalInfoService {
    BookDto bookDto;
    BookRentalDto bookRentalDto;
    MemberDto memberDto;
    BookRepository bookRepository;
    RentalRepository rentalRepository;
    MemberRepository memberRepository;
    BookMapper bookMapper = new BookMapper();
    RentalMapper rentalMapper = new RentalMapper();
    MemberMapper memberMapper = new MemberMapper();

    @Autowired
    public BookDetailsWithRentalInfoService(BookRepository bookRepository, RentalRepository rentalRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
    }

    public BookDetailsWithLentInfoDto fillInAllDtosByBookID(String bookID) {
        BookDetailsWithLentInfoDto bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(null,null,null);
        if (StringUtils.isEmpty(bookID)) return bookDetailsWithLentInfoDto  ;
        Book aBook = bookRepository.getById(bookID);
        if (aBook == null) return bookDetailsWithLentInfoDto ;
        bookDto = bookMapper.mapToDto(aBook);
        bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto,null,null);
        if (rentalRepository.isBookIDInRepository(aBook.getId())) {
            BookLent aBookLent = rentalRepository.getLentByBookID(aBook.getId());
            bookRentalDto = rentalMapper.mapToDto(aBookLent);
            bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto, bookRentalDto,null);
            if (memberRepository.isMemberIDInRepository(aBookLent.getLendeeID())) {
                Member aMember = memberRepository.getMemberByID(aBookLent.getLendeeID());
                memberDto = memberMapper.convertMemberToDto(aMember);
                bookDetailsWithLentInfoDto = new BookDetailsWithLentInfoDto(bookDto, bookRentalDto,memberDto);
            }
        }
        return bookDetailsWithLentInfoDto;
    }
}