package com.testingtigers.api;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    public static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BookDto> getAllBooks() {
        return bookService.makeListOfBookDtos();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto getSpecificBook(@PathVariable("id") String id) {
        return bookService.returnSpecificBookBasedOnId(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)

    public BookDto createBook(CreateBookDto createdBookDto){
        return bookService.createBookDto(createdBookDto);

    }

    @GetMapping(path = "/delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto deleteBookByID(@PathVariable("id") String id) {
        //usage localhost:8080/books\ISBN\123-456-danny
        return bookService.deleteBookByID(id);
    }
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

    @GetMapping(path = "/author/", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BookDto> getBookByAuthor(
            //usage localhost:8080/books/author/?firstName=*&lastName=*
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        return bookService.returnBooksByAuthor(firstName, lastName);
    }


}
