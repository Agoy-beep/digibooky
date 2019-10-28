package com.testingtigers.api;

import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.domain.exceptions.AuthorNotFound;
import com.testingtigers.domain.exceptions.BookNotFound;
import com.testingtigers.service.AuthorService;
import com.testingtigers.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    public static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BookDto> getAllBooks() {
        logger.info("A list of all the books in the database was queried.");
        return bookService.makeListOfBookDtos();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto getSpecificBook(@PathVariable("id") String id) {
        logger.info("A book was queried with ID:" + id + ".");
        return bookService.returnSpecificBookBasedOnId(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createdBookDto){

        //TODO CHECK THIS ^^
        logger.info("User attempts to create a book titled :" + createdBookDto.getTitle() +  ".");
        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(createdBookDto.getAuthorLastName());
        if(createdBookDto.getSummary().equals("BLANK")){
            return bookService.registerBookAndReturnDto(createdBookDto, authorDto);
        } else{

            return bookService.registerBookAndReturnDto(createdBookDto);
        }
    }

    @GetMapping(path = "/delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto deleteBookByID(@PathVariable("id") String id) {
        logger.info("User attempted to delete book with ID: " + id + ".");
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.deleteBookByID(id);
    }
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
    @PutMapping(params = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@RequestParam ("id") String id, @RequestBody UpdateBookDto updateBookDto){
        logger.info("User attempted to update book with title:" + updateBookDto.getTitle() + ".");
        return bookService.updateSpecificBook(id, updateBookDto);
    }

    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        logger.info("User attempted to retrieve a list of books by author: " + firstName + " " + lastName + "." );
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
}
