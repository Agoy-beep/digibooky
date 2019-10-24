package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
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
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.mapper = new Mapper();
    }

    public List<BookDto> makeListOfBookDtos() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            bookDtos.add(mapper.mapToDto(book));
        }

        return bookDtos;
    }

    public BookDto returnSpecificBookBasedOnId(String id){
        return mapper.mapToDto(bookRepository.getById(id));
    }

    public BookDto createBook(CreateBookDto createBookDto){
        bookRepository.addBookToDataBase(mapper.mapToBook(createBookDto));
        return mapper.mapToDto(mapper.mapToBook(createBookDto));
    }
}
