package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;

public class Mapper {


    public Book mapToBook(CreateBookDto createBookDto){
        return new Book(createBookDto.getIsbn(), createBookDto.getTitle(), createBookDto.getAuthorID(), createBookDto.getSummary());
    }

    public BookDto mapToDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setAuthorID(book.getAuthorID());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setSummary(book.getSummary());

        return bookDto;

    }
}
