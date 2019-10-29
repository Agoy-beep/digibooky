package com.testingtigers.api;

import com.testingtigers.domain.dtos.*;
import com.testingtigers.domain.exceptions.AuthorNotFound;
import com.testingtigers.domain.exceptions.BookNotFound;
import com.testingtigers.domain.exceptions.EmptyFields;
import com.testingtigers.service.AuthorService;
import com.testingtigers.service.BookDetailsWithRentalInfoService;
import com.testingtigers.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookDetailsWithRentalInfoService bookDetailsWithRentalInfoService;
    public static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, BookDetailsWithRentalInfoService bookDetailsWithRentalInfoService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookDetailsWithRentalInfoService = bookDetailsWithRentalInfoService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BookDto> getAllBooks() {
        logger.info("A list of all the books in the database was queried.");
        return bookService.makeListOfBookDtos();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDetailsWithLentInfoDto getSpecificBook(@PathVariable("id") String id) {
        logger.info("A book was queried with ID:" + "\"" + id +"\"" + ".");

        return bookDetailsWithRentalInfoService.fillInAllDtosByBookID(id);
    }

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createdBookDto){
        logger.info("User attempts to create a book titled: " + "\"" + createdBookDto.getTitle() + "\"" +  ".");

        if(StringUtils.isEmpty(createdBookDto.getTitle())|| StringUtils.isEmpty(createdBookDto.getIsbn()) ||
                StringUtils.isEmpty(createdBookDto.getAuthorLastName())){
            throw new EmptyFields(HttpStatus.BAD_REQUEST, "Some fields don't have input for " +
                    "the book you are trying to create!");
        }
        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(createdBookDto.getAuthorLastName());
        if(StringUtils.isEmpty(createdBookDto.getSummary())){
            return bookService.registerBookAndReturnDto(createdBookDto, authorDto);
        } else{

            return bookService.registerBookAndReturnDto(createdBookDto);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_BOOK')")
    @GetMapping(path = "/delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto deleteBookByID(@PathVariable("id") String id) {
        logger.info("User deleted book with ID: " + "\"" + id +"\"" + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.deleteBookByID(id);
    }

    @PreAuthorize("hasAuthority('UNDELETE_BOOK')")
    @GetMapping(path = "/undelete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto undeleteBookByID(@PathVariable("id") String id) {
        logger.info("User restored book with ID: " + "\"" + id +"\"" + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.undeleteBookByID(id);
    }

    @GetMapping(path = "/ISBN/{ISBN}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByISBN(@PathVariable("ISBN") String ISBN) {
        logger.info("User looked for books with ISBN: " + "\"" + ISBN + "\"" + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.returnBooksByISBN(ISBN);
    }

    @GetMapping(path = "/title/{title}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByTitle(@PathVariable("title") String title) {
        logger.info("User looked for books with title: " + "\"" +title + "\"" + ".");
        return bookService.returnBooksByTitle(title);
    }

    @PreAuthorize("hasAuthority('UPDATE_BOOK')")
    @PutMapping(params = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@RequestParam ("id") String id, @RequestBody UpdateBookDto updateBookDto){
        if(StringUtils.isEmpty(updateBookDto.getTitle())|| StringUtils.isEmpty(updateBookDto.getSummary()) ||
                StringUtils.isEmpty(updateBookDto.getAuthorID())){
            throw new EmptyFields(HttpStatus.BAD_REQUEST, "Some fields don't have input for the book you are trying to update! ");
        }
        logger.info("User attempted to update book with title: " + "\"" + updateBookDto.getTitle() + "\"" + ".");
        return bookService.updateSpecificBook(id, updateBookDto);
    }

    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        logger.info("User looked for a list of books by author: " + "\"" + firstName+ "\"" + " " + "\"" + lastName + "\"" + "." );
        return bookService.returnBooksByAuthor(firstName, lastName);
    }

    @ExceptionHandler(BookNotFound.class)
    protected void bookNotFound(BookNotFound ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for book that was unavailable. Message: " + ex.getMessage());
    }

    @ExceptionHandler(AuthorNotFound.class)
    protected void authorNotFound(AuthorNotFound ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for author that was not in the database. Message: " + ex.getMessage());
    }

    @ExceptionHandler(EmptyFields.class)
    protected void fieldsAreEmpty(EmptyFields ex, HttpServletResponse response) throws IOException{
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User did not provide input for all the relevant fields. Message:" + ex.getMessage());
    }

}
