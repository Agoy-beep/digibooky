package com.testingtigers.service;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.dtos.UpdateBookDto;
import com.testingtigers.domain.exceptions.AuthorNotFound;
import com.testingtigers.domain.exceptions.BookNotFound;
import com.testingtigers.domain.repositories.AuthorRepository;
import com.testingtigers.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BookService {

    private final BookRepository bookRepository;

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = new BookMapper();
        this.authorRepository = authorRepository;

        Author authorToAdd = new Author("authorDannyFirstName", "authorDannyLastName");
        authorRepository.addAuthor(authorToAdd);

        Book bookToAdd = new Book("123-456-danny" , "DannyTitle",  authorToAdd.getAuthorID(),"DannySummery");
        bookRepository.addBookToDataBase(bookToAdd);
    }

    public List<BookDto> makeListOfBookDtos() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            bookDtos.add(bookMapper.mapToDto(book));
        }

        return bookDtos;
    }

    public BookDto returnSpecificBookBasedOnId(String id){
        if(bookRepository.getById(id) == null){
            throw new BookNotFound(HttpStatus.BAD_REQUEST, "Book by id: " + id + " was not found.");
        }
        return bookMapper.mapToDto(bookRepository.getById(id));
    }

    public BookDto registerBookAndReturnDto(CreateBookDto createBookDto, AuthorDto authorDto){
        Book newBook = bookMapper.mapToBook(createBookDto, authorDto);
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
    public List<BookDto> returnBooksByAuthor(String firstName,String lastName) {
        if(bookRepository.getBookByAuthor(firstName,lastName,authorRepository).size() == 0){
            throw new AuthorNotFound(HttpStatus.BAD_REQUEST, "That author does not have any books in our database");
        }
        return bookRepository.getBookByAuthor(firstName,lastName,authorRepository);
    }

    public BookDto deleteBookByID(String id) {
        return bookRepository.deleteBookFromDatabaseByID(id);
    }

    public BookDto undeleteBookByID(String id) {
        return bookRepository.undeleteBookFromDatabaseByID(id);
    }

}
