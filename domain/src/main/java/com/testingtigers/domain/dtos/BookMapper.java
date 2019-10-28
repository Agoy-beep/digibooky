package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.BookBuilder;
import com.testingtigers.domain.repositories.AuthorRepository;

public class BookMapper {



    public Book mapToBook(CreateBookDto createBookDto, AuthorDto authorDto) {
        BookBuilder newBookWithOutSummary = new BookBuilder();
        return newBookWithOutSummary.createBookWithoutSummary(createBookDto.getIsbn(), createBookDto.getTitle(),
                authorDto.getAuthorID());
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
