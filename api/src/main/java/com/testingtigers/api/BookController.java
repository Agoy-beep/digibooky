package com.testingtigers.api;

import com.testingtigers.domain.dtos.Mapper;
import com.testingtigers.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookRepository bookRepository;
    private final Mapper mapper;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.mapper = new Mapper();
    }


}
