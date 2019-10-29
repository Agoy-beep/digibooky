package com.testingtigers.api;

import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.exceptions.BookIsAlreadyLentOut;
import com.testingtigers.domain.exceptions.LentBadFormError;
import com.testingtigers.service.LentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/lent")
public class LentController {

    private final LentService lentService;
    private final LendMapper lendMapper;
    public static Logger logger = LoggerFactory.getLogger(LentController.class);

    @Autowired
    public LentController(LentService lentService, LendMapper lendMapper) {
        this.lentService = lentService;
        this.lendMapper = lendMapper;
    }

    @PreAuthorize("hasAuthority('GET_LENT_BOOKS')")
    @GetMapping(path = "", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080\lent
    public List<BookLentDto> getLentBooks() {
        System.out.println("in de lent");
        return lentService.getAllLentBooksAsDto();
    }

    @PreAuthorize("hasAuthority('BORROW_BOOK')")
    @GetMapping(path = "/lentbook", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080/lent/lentbook?bookID=20&memberID=20&startDateToLent=29/01/1973
    public BookLentDto lentBook(
            @RequestParam("bookID") String bookID,
            @RequestParam("memberID") String memberID,
            @RequestParam("startDateToLent") String startDateToLentAsString) {
        if (bookID.isEmpty() || memberID.isEmpty() || startDateToLentAsString.isEmpty()) {

            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Please provide arguments as  bookID memberID dd/MM/yyyy"); }


        Date startDateToLent;
        try {
            startDateToLent = new SimpleDateFormat("dd/MM/yyyy").parse(startDateToLentAsString);
        } catch (Exception ex) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Use date format dd/MM/yyyy");
        }
        BookLent bookToLent = lentService.addBookToLent(bookID, memberID, startDateToLent);
        return lendMapper.convertBookLentToDto(bookToLent);
    }

    @PreAuthorize("hasAuthority('GET_LENT_BOOKS_BY_MEMBER')")
    @GetMapping(path = "/lentbymember/{memberID}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> lentBooksByMember(@PathVariable("memberID") String memberID) {
        if (memberID.isEmpty()) throw new IllegalArgumentException("Parameter memberID missing");

        return lentService.lentBooksByMember(memberID);
    }

    @PreAuthorize("hasAuthority('GET_OVERDUE_BOOKS')")
    @GetMapping(path = "/lentoverdue", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    // usage localhost:8080/lent/lentoverdue?dateToCheck=28/11/2019
    public List<BookDto> lentOverdue(@RequestParam("dateToCheck") String dateToCheckAsString) {

        Date dateToCheck;
        try {
            dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(dateToCheckAsString);
        } catch (Exception ex) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Use date format dd/MM/yyyy");
        }

        return lentService.getAllBooksOverdue(dateToCheck);
    }


    @ExceptionHandler(LentBadFormError.class)
    protected void lentBadForm(LentBadFormError ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User made mistakes filling lent form.");
    }

    @ExceptionHandler(BookIsAlreadyLentOut.class)

    protected void bookIsAlreadyLentOut(BookIsAlreadyLentOut ex, HttpServletResponse response) throws IOException{
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for book that was already lent out.");
    }

    /*
   "isbn": "123-456-danny",
        "uniqueId": "b4c4414e-6450-44cc-9cc6-1f1b3c412f38",
        "title": "DannyTitle",
        "authorID": "8c7be2ea-72f4-4543-b613-c9fa27306ff9",
        "summary": "DannySummery"

       "id": "10d3baf7-8447-42bd-b60d-bae76e2f2d59",
        "emailAdress": "jesus@heaven.hell",
        "firstName": null,
        "lastName": "christ",
        "city": "bethlehem",
        "postalCode": null,
        "streetName": null,
        "streetNumber": null,
        "inss": "Hidden for privacy reasons."

        {
    "bookID": "b4c4414e-6450-44cc-9cc6-1f1b3c412f38",
    "lendeeID": "10d3baf7-8447-42bd-b60d-bae76e2f2d59",
    "lentStartDate": "2019-10-27T23:00:00.000+0000",
    "lentEndDate": "2019-11-17T23:00:00.000+0000",
    "lentID": "23d6d5cc-37aa-4cfe-a9cf-d21c3424c0e7"
}

*/
}

