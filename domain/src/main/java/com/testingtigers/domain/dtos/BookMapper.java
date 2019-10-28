package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;

public class BookMapper {


    public Book mapToBook(CreateBookDto createBookDto){
        //TODO user Author Last Name to get the ID.
        return new Book(createBookDto.getIsbn(), createBookDto.getTitle(), createBookDto.getAuthorLastName());
    }

    public Book mapToBook(UpdateBookDto updateBookDto){
        return new Book(updateBookDto.getTitle(), updateBookDto.getAuthorID(), updateBookDto.getSummary());
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
