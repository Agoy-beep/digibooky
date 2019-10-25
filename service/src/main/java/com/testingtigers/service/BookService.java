package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = new BookMapper();
    }

    public List<BookDto> makeListOfBookDtos() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            bookDtos.add(bookMapper.mapToDto(book));
        }

        return bookDtos;
    }

    public BookDto returnSpecificBookBasedOnId(String id){
        return bookMapper.mapToDto(bookRepository.getById(id));
    }

    public BookDto registerBookAndReturnDto(CreateBookDto createBookDto){
        Book newBook = bookMapper.mapToBook(createBookDto);
        bookRepository.addBookToDataBase(newBook);
        return bookMapper.mapToDto(newBook);
    }

    public BookDto updateSpecificBook(String id, UpdateBookDto updateBookDto){
        Book bookToUpdate = bookRepository.getById(id);
        Book bookWithNewInfo = bookMapper.mapToBook(updateBookDto);
        bookToUpdate.setAuthorID(bookWithNewInfo.getAuthorID());
        bookToUpdate.setTitle(bookWithNewInfo.getTitle());
        bookToUpdate.setSummary(bookWithNewInfo.getSummary());
        return bookMapper.mapToDto(bookToUpdate);
    }
    public BookRepository getBookRepository() { return bookRepository;}

    public List<BookDto> returnBooksByISBN(String isbn) {
        return bookRepository.getBookByISBN(isbn);
    }

    public List<BookDto> returnBooksByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }
}
