package com.testingtigers.api;

import com.testingtigers.domain.dtos.*;
import com.testingtigers.domain.exceptions.AuthorNotFound;
import com.testingtigers.domain.exceptions.BookNotFound;
import com.testingtigers.service.AuthorService;
import com.testingtigers.service.BookDetailsWithRentalInfoService;
import com.testingtigers.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    //public BookDto getSpecificBook(@PathVariable("id") String id) {
    public BookDetailsWithLentInfoDto getSpecificBook(@PathVariable("id") String id) {
        logger.info("A book was queried with ID:" + id + ".");

        return bookDetailsWithRentalInfoService.fillInAllDtosByBookID(id);
        // return bookService.returnSpecificBookBasedOnId(id);
    }

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createdBookDto) {
        logger.info("User attempts to create a book titled: " + createdBookDto.getTitle() + ".");
        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(createdBookDto.getAuthorLastName());
        if (createdBookDto.getSummary() == null) {
            return bookService.registerBookAndReturnDto(createdBookDto, authorDto);
        } else {

            return bookService.registerBookAndReturnDto(createdBookDto);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_BOOK')")
    @GetMapping(path = "/delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto deleteBookByID(@PathVariable("id") String id) {
        logger.info("User attempted to delete book with ID: " + id + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.deleteBookByID(id);
    }

    @PreAuthorize("hasAuthority('UNDELETE_BOOK')")
    @GetMapping(path = "/undelete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto undeleteBookByID(@PathVariable("id") String id) {
        logger.info("User attempted to undelete book with ID: " + id + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.undeleteBookByID(id);
    }

    @GetMapping(path = "/ISBN/{ISBN}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByISBN(@PathVariable("ISBN") String ISBN) {
        logger.info("User attempted to find books with ISBN: " + ISBN + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.returnBooksByISBN(ISBN);
    }

    @GetMapping(path = "/title/{title}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByTitle(@PathVariable("title") String title) {
        logger.info("User attempted to find books with title: " + title + ".");
        return bookService.returnBooksByTitle(title);
    }

    @PreAuthorize("hasAuthority('UPDATE_BOOK')")
    @PutMapping(params = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@RequestParam("id") String id, @RequestBody UpdateBookDto updateBookDto) {
        logger.info("User attempted to update book with title:" + updateBookDto.getTitle() + ".");
        return bookService.updateSpecificBook(id, updateBookDto);
    }

    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        logger.info("User attempted to retrieve a list of books by author: " + firstName + " " + lastName + ".");
        return bookService.returnBooksByAuthor(firstName, lastName);
    }

    @ExceptionHandler(BookNotFound.class)
    protected void bookNotFound(BookNotFound ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for book that was unavailable.");
    }

    @ExceptionHandler(AuthorNotFound.class)
    protected void authorNotFound(AuthorNotFound ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        logger.warn("User looked for author that was not in the database.");
    }
    /*
 {
        "isbn": "123-456-danny",
        "uniqueId": "e2b44dc7-12bd-402c-bb9f-c454a627a595",
        "title": "DannyTitle",
        "authorID": "f656d65b-f3e1-4c4b-835a-40ff23f9058b",
        "summary": "DannySummery"
    }



     */



}
