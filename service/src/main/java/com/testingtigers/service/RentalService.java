package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.TicketAfterReturn;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookRentalDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.exceptions.BookIsAlreadyLentOut;
import com.testingtigers.domain.exceptions.LentBadFormError;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.domain.repositories.RentalRepository;
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
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    @Autowired
    public RentalService(RentalRepository rentalRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public BookLent addBookToLent(String bookID, String lenderID, Date startDateToLent) {

        if ((StringUtils.isEmpty( bookID)) || (StringUtils.isEmpty(lenderID) || startDateToLent == null))
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Member or book or date is null or empty. Please retry with both values");
        if (bookRepository.getById(bookID) == null) return null; // exception is catched in bookrepository

        if (memberRepository.getMemberByID(lenderID) == null) return null; // exception is catched memberrepository
        if (rentalRepository.isBookIDInRepository(bookID))
            throw new BookIsAlreadyLentOut(HttpStatus.BAD_REQUEST, "Book is already lent out.");

        return rentalRepository.
                addBookToLent(bookRepository.getById(bookID),
                        memberRepository.getMemberByID(lenderID),
                        startDateToLent);
    }

    @Bean
    public List<BookLent> getAllLentBooks() {
        return rentalRepository.getAllLentsAsList();
    }

    @Bean
    public List<BookRentalDto> getAllLentBooksAsDto() {
        return rentalRepository.getAllLentsAsListDto();
    }

    public List<BookDto> lentBooksByMember(String memberID) {

        if (StringUtils.isEmpty(memberID)) {
            throw new BookIsAlreadyLentOut(HttpStatus.BAD_REQUEST, "No sush member");
        }

        List<BookDto> bookDtos = new ArrayList<>();

        List<BookRentalDto> bookRentalDtos = rentalRepository.getLentBooksByMember(memberID);

        if (bookRentalDtos == null) return bookDtos;

        for (BookRentalDto bookRentalDto : bookRentalDtos) {
            BookDto bookDto = bookMapper.mapToDto(bookRepository.getById(bookRentalDto.getBookID()));
            if (bookDto == null) continue;
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    public List<BookDto> getAllBooksOverdue(Date dateToCheck) {
        List<BookRentalDto> bookRentalDtos = rentalRepository.getAllBookLentsOverdue(dateToCheck);
        List<BookDto> result = new ArrayList<>();

        for (BookRentalDto bookRentalDto : bookRentalDtos) {
            Book book = bookRepository.getById(bookRentalDto.getBookID());
            if (book == null) continue;
            result.add(bookMapper.mapToDto(book));
        }

        return result;
    }

    public TicketAfterReturn returnLentBook(String lentID, Date returnDate) {
        if (returnDate == null) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "returnDate is null. Please retry with a valid returnDate");
        }
        if (StringUtils.isEmpty(lentID)) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "lentID is null. Please retry with a lentID");
        }
        if (!rentalRepository.isLentIDInRepository(lentID)) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "lentID is not in rented list. Please retry with a different lentID");
        }

        BookRentalDto bookRentalDto = rentalRepository.getLentDtoByLentID(lentID);
        if (bookRentalDto == null) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Something went wrong with bookID");
        }
        TicketAfterReturn resultTicket =
                new TicketAfterReturn(
                        returnDate,
                        bookRentalDto.getLentStartDate(),
                        bookRentalDto.getLentEndDate(),
                        bookRentalDto.getBookID(),
                        bookRentalDto.getLendeeID(),
                        bookRentalDto.getLentID()
                );
        rentalRepository.deleteLentByLentID(bookRentalDto.getLentID());
        return resultTicket;
    }
}
