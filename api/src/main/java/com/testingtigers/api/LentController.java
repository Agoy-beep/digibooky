package com.testingtigers.api;

import com.testingtigers.domain.BookLent;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.service.LentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/lent")
public class LentController {

    private final LentService lentService;
    public static Logger logger = LoggerFactory.getLogger(LentController.class);

    @Autowired
    public LentController(LentService lentService) {
        this.lentService = lentService;
    }

    @GetMapping(path = "", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080\lent
    public List<BookLent> getLentBooks() {
        System.out.println("in de lent");
        return lentService.getAllLentBooks();
    }
    /*
    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        return bookService.returnBooksByAuthor(firstName, lastName);
    }
     */

    @GetMapping(path = "/lentbook", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    //usage localhost:8080/lent/lentbook?bookID=20&memberID=20&startDateToLent=29/01/1973
    public BookLent lentBook(
            @RequestParam("bookID") String bookID,
            @RequestParam("memberID") String memberID,
            @RequestParam("startDateToLent") String startDateToLentAsString) {
        System.out.println("in de lentbook");
        if (bookID.isEmpty() || memberID.isEmpty() || startDateToLentAsString.isEmpty()) {
            throw new IllegalArgumentException("Please provide arguments as  bookID memberID dd/MM/yyyy");
        }
        System.out.println("bookId :  "+bookID);
        System.out.println("memberId :  "+memberID);
        System.out.println("startDate :  "+startDateToLentAsString);
        Date startDateToLent;
        try {
            startDateToLent = new SimpleDateFormat("dd/MM/yyyy").parse(startDateToLentAsString);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Use date format dd/MM/yyyy");
        }
        return lentService.addBookToLent(bookID, memberID, startDateToLent );
    }


}

