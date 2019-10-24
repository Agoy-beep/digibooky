package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;

public class Mapper {


    public Book mapToBook(BookDto bookDto){
        return new Book(bookDto.getIsbn(), bookDto.getAuthorID(), bookDto.getSummary(), bookDto.getTitle());
    }

    public BookDto mapToDto(Book book){
        return new BookDto(book.getIsbn(), book.getAuthorID(), book.getId(), book.getSummary(), book.getTitle());
    }
}
