package com.testingttigers.service;


import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.Mapper;
import com.testingtigers.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    private final BookRepository bookRepository;
    private final Mapper mapper;

    @Autowired
    public BookService(BookRepository bookRepository, Mapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<BookDto> makeListOfBookDtos(){
        List<BookDto> listOfBooksDto = new ArrayList<>();
        for(Book book : bookRepository.getAllBooks()){
            listOfBooksDto.add(mapper.mapToDto(book));
        }
        return listOfBooksDto;
    }
}
