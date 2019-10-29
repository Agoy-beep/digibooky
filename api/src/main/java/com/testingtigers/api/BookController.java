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
    public static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BookDto> getAllBooks() {
        return bookService.makeListOfBookDtos();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto getSpecificBook(@PathVariable("id") String id) {
        return bookService.returnSpecificBookBasedOnId(id);
    }

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createdBookDto){
        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(createdBookDto.getAuthorLastName());
        return bookService.registerBookAndReturnDto(createdBookDto, authorDto);
    }

    @PreAuthorize("hasAuthority('DELETE_BOOK')")
    @GetMapping(path = "/delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto deleteBookByID(@PathVariable("id") String id) {
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.deleteBookByID(id);
    }

    @PreAuthorize("hasAuthority('UNDELETE_BOOK')")
    @GetMapping(path = "/undelete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto undeleteBookByID(@PathVariable("id") String id) {
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.undeleteBookByID(id);
    }

    @GetMapping(path = "/ISBN/{ISBN}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByISBN(@PathVariable("ISBN") String ISBN) {
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.returnBooksByISBN(ISBN);
    }

    @GetMapping(path = "/title/{title}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByTitle(@PathVariable("title") String title) {
        return bookService.returnBooksByTitle(title);
    }

    @PreAuthorize("hasAuthority('UPDATE_BOOK')")
    @PutMapping(params = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@RequestParam ("id") String id, @RequestBody UpdateBookDto updateBookDto){
        return bookService.updateSpecificBook(id, updateBookDto);
    }

    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
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
