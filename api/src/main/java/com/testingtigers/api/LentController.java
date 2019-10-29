package com.testingtigers.api;

import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.TicketAfterReturn;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.domain.exceptions.BookIsAlreadyLentOut;
import com.testingtigers.domain.exceptions.LentBadFormError;
import com.testingtigers.service.LentService;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final LendMapper lendMapper = new LendMapper();
    public static Logger logger = LoggerFactory.getLogger(LentController.class);

    @Autowired
    public LentController(LentService lentService) {

        this.lentService = lentService;
    }

    @GetMapping(path = "", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080\lent
    public List<BookLentDto> getLentBooks() {
        System.out.println("in de lent");
        return lentService.getAllLentBooksAsDto();
    }

    @GetMapping(path = "/lentbook", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080/lent/lentbook?bookID=20&memberID=20&startDateToLent=29/01/1973
    public BookLentDto lentBook(
            @RequestParam("bookID") String bookID,
            @RequestParam("memberID") String memberID,
            @RequestParam("startDateToLent") String startDateToLentAsString) {
        if (bookID.isEmpty() || memberID.isEmpty() || startDateToLentAsString.isEmpty()) {

            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Please provide arguments as  bookID memberID dd/MM/yyyy");
        }

        Date startDateToLent;
        try {
            startDateToLent = new SimpleDateFormat("dd/MM/yyyy").parse(startDateToLentAsString);
        } catch (Exception ex) {
            throw new LentBadFormError(HttpStatus.BAD_REQUEST, "Use date format dd/MM/yyyy");
        }
        BookLent bookToLent = lentService.addBookToLent(bookID, memberID, startDateToLent);
        return lendMapper.convertBookLentToDto(bookToLent);
    }

    @GetMapping(path = "/lentbymember/{memberID}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> lentBooksByMember(@PathVariable("memberID") String memberID) {
        if (memberID.isEmpty()) throw new IllegalArgumentException("Parameter memberID missing");

        return lentService.lentBooksByMember(memberID);
    }

    @GetMapping(path = "/lentoverdue", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    // usage localhost:8080/lent/lentoverdue?dateToCheck=28/11/2019
    public List<BookDto> lentOverdue(@RequestParam("dateToCheck") String dateToCheckAsString) {

        Date dateToCheck;
        try {
            dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(dateToCheckAsString);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Use date format dd/MM/yyyy");
        }

        return lentService.getAllBooksOverdue(dateToCheck);
    }

    @GetMapping(path = "/lentreturn", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    // usage
    public TicketAfterReturn lentReturn(
            @RequestParam("bookID") String bookID,
            @RequestParam("dateToCheck") String dateToCheckAsString) {
        Date dateToCheck;
        try {
            dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(dateToCheckAsString);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Use date format dd/MM/yyyy");
        }
        return lentService.returnLentBook(bookID, dateToCheck);
    }

    @ExceptionHandler(LentBadFormError.class)
    protected void lentBadForm(LentBadFormError ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User made mistakes filling lent form.");
    }

    @ExceptionHandler(BookIsAlreadyLentOut.class)

    protected void bookIsAlreadyLentOut(BookIsAlreadyLentOut ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for book that was already lent out.");
    }

    /*
{
        "isbn": "123-456-danny",
        "uniqueId": "845e5f64-f7e7-424a-a8e7-676897187b1b",
        "title": "DannyTitle",
        "authorID": "71591420-6072-49ba-acb0-6468d400d049",
        "summary": "DannySummery"
    }

    {
        "id": "c555acdc-e82e-422f-81af-eb5645de268d",
        "emailAdress": "jesus@heaven.hell",
        "firstName": null,
        "lastName": "christ",
        "city": "bethlehem",
        "postalCode": null,
        "streetName": null,
        "streetNumber": null,
        "inss": "Hidden for privacy reasons."
    }

     "bookID": "845e5f64-f7e7-424a-a8e7-676897187b1b",
        "lendeeID": "c555acdc-e82e-422f-81af-eb5645de268d",
        "lentStartDate": "2019-10-28T23:00:00.000+0000",
        "lentEndDate": "2019-11-18T23:00:00.000+0000",
        "lentID": "48d22bc9-c127-497d-9dc7-8c43b2fb0939"


     */
}

