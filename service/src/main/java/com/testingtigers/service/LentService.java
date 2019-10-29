package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.TicketAfterReturn;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.exceptions.BookIsAlreadyLentOut;
import com.testingtigers.domain.exceptions.LentBadFormError;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.domain.repositories.LentRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LentService {
    private final LentRepository lentRepository;
    private final MemberRepository memberRepository;
    private BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    @Autowired
    public LentService(LentRepository lentRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.lentRepository = lentRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public BookLent addBookToLent(String bookID, String lenderID, Date startDateToLent) {

        if ((StringUtils.isEmpty( bookID)) || (StringUtils.isEmpty(lenderID) || startDateToLent == null))
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Member or book or date is null or empty. Please retry with both values");
        if (bookRepository.getById(bookID) == null) return null; // exception is catched in bookrepository

        if (memberRepository.getMemberByID(lenderID) == null) return null; // exception is catched memberrepository
        if (lentRepository.isBookIDInRepository(bookID))
            throw new BookIsAlreadyLentOut(HttpStatus.BAD_REQUEST, "Book is already lent out.");

        return lentRepository.
                addBookToLent(bookRepository.getById(bookID),
                        memberRepository.getMemberByID(lenderID),
                        startDateToLent);
    }

    @Bean
    public List<BookLent> getAllLentBooks() {
        return lentRepository.getAllLentsAsList();
    }

    @Bean
    public List<BookLentDto> getAllLentBooksAsDto() {
        return lentRepository.getAllLentsAsListDto();
    }

    public List<BookDto> lentBooksByMember(String memberID) {

        if (StringUtils.isEmpty(memberID)) {
            throw new BookIsAlreadyLentOut(HttpStatus.BAD_REQUEST, "No sush member");
        }

        List<BookDto> bookDtos = new ArrayList<>();

        List<BookLentDto> bookLentDtos = lentRepository.getLentBooksByMember(memberID);

        if (bookLentDtos == null) return bookDtos;

        for (BookLentDto bookLentDto : bookLentDtos) {
            BookDto bookDto = bookMapper.mapToDto(bookRepository.getById(bookLentDto.getBookID()));
            if (bookDto == null) continue;
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    public List<BookDto> getAllBooksOverdue(Date dateToCheck) {
        List<BookLentDto> bookLentDtos = lentRepository.getAllBookLentsOverdue(dateToCheck);
        List<BookDto> result = new ArrayList<>();

        for (BookLentDto bookLentDto : bookLentDtos) {
            Book book = bookRepository.getById(bookLentDto.getBookID());
            if (book == null) continue;
            result.add(bookMapper.mapToDto(book));
        }

        return result;
    }

    public TicketAfterReturn returnLentBook(String bookID, Date returnDate) {
        if (returnDate == null) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "returnDate is null. Please retry with a valid returnDate");
        }
        if (StringUtils.isEmpty(bookID)) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "bookID is null. Please retry with a bookID");
        }
        if (!lentRepository.isBookIDInRepository(bookID)) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "bookID is not in rented list. Please retry with a different bookID");
        }

        BookLentDto bookLentDto = lentRepository.getLentDtoByBookID(bookID);
        if (bookLentDto == null) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Something went wrong with bookID");
        }
        TicketAfterReturn resultTicket =
                new TicketAfterReturn(
                        returnDate,
                        bookLentDto.getLentStartDate(),
                        bookLentDto.getLentEndDate(),
                        bookLentDto.getBookID(),
                        bookLentDto.getLendeeID(),
                        bookLentDto.getLentID()
                );
        lentRepository.deleteLentByLentID(bookLentDto.getLentID(),returnDate);
        return resultTicket;
    }
}
