package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;

public class BookMapper {


    public Book mapToBook(CreateBookDto createBookDto){
        return new Book(createBookDto.getIsbn(), createBookDto.getTitle(), createBookDto.getAuthorID(), createBookDto.getSummary());
    }

    public BookDto mapToDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setAuthorID(book.getAuthorID());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setSummary(book.getSummary());
        bookDto.setUniqueId(book.getId());

        return bookDto;

    }
}
