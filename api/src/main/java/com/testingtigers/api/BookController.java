package com.testingtigers.api;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.dtos.Mapper;
import com.testingtigers.domain.repositories.BookRepository;
import com.testingtigers.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BookDto> getAllBooks(){
        return bookService.makeListOfBookDtos();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public BookDto getSpecificBook(@PathVariable ("id") String id){
        return bookService.returnSpecificBookBasedOnId(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(CreateBookDto createBookDto){
        return bookService.createBook(createBookDto);
    }


}
