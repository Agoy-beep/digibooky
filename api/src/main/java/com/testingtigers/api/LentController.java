package com.testingtigers.api;

import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookLentDto;
import com.testingtigers.domain.dtos.LendMapper;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.domain.exceptions.BookIsAlreadyLentOut;
import com.testingtigers.domain.exceptions.LentBadFormError;
import com.testingtigers.service.LentService;
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
    public LentController(LentService lentService ) {

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
    /*


     */
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
        BookLent bookToLent = lentService.addBookToLent(bookID, memberID, startDateToLent );
        return lendMapper.convertBookLentToDto( bookToLent);
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
        "uniqueId": "04ff6169-043d-45c2-8ad0-778b3bdd58f0",
        "title": "DannyTitle",
        "authorID": "8eaa7959-f5f5-4a99-abd3-996253659d93",
        "summary": "DannySummery"

         "id": "3016d6b6-86e1-4a07-b021-1f4b641c445e",
        "emailAdress": "jesus@heaven.hell",
        "firstName": null,
        "lastName": "christ",
        "city": "bethlehem",
        "postalCode": null,
        "streetName": null,
        "streetNumber": null,
        "inss": "Hidden for privacy reasons."

     */


}

