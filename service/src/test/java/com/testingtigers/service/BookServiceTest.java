package com.testingtigers.service;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.*;
import com.testingtigers.domain.repositories.AuthorRepository;
import com.testingtigers.domain.repositories.BookDataBaseDummy;
import com.testingtigers.domain.repositories.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest {

    BookDataBaseDummy dumdum = new BookDataBaseDummy();
    BookRepository bookRepository = new BookRepository(dumdum);
    AuthorRepository authorRepository = new AuthorRepository();
    BookService bookService = new BookService(bookRepository,authorRepository);
    BookMapper bookMapper = new BookMapper();
    AuthorMapper authorMapper = new AuthorMapper();

    @Test
    void makeListOfBookDtos() {
        //GIVEN
        List<BookDto> bookDtos = new ArrayList<>();

        //WHEN
        for(Book book : bookRepository.getAllBooks()){
            bookDtos.add(bookMapper.mapToDto(book));
        }
        //THEN
        assertThat(bookDtos.size()).isEqualTo(bookRepository.getAllBooks().size());
    }

    @Test
    void returnSpecificBookBasedOnId() {
        //GIVEN
        Book book = new Book ("ISBN", "TITLE", "AUTHOR2", "SUMS");
        bookRepository.addBookToDataBase(book);
        //WHEN
        BookDto bookDto = bookMapper.mapToDto(bookRepository.getById(book.getId()));
        //THEN
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void createBook() {
        //GIVEN

        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setIsbn("ISBN");
        createBookDto.setAuthorLastName("Willis");
        createBookDto.setTitle("Title");

        Author newAuthor = new Author("Blank", createBookDto.getAuthorLastName());
        AuthorDto authorDto = authorMapper.mapToDto(newAuthor);
        //WHEN
        BookDto newBookDto = bookService.registerBookAndReturnDto(createBookDto, authorDto);
        //THEN
        assertThat(newBookDto.getTitle()).isEqualTo("Title");
        assertThat(newBookDto.getUniqueId()).isNotBlank();
        assertThat(newBookDto.getAuthorID()).isEqualTo(authorDto.getAuthorID());
        assertThat(newBookDto.getIsbn()).isEqualTo("ISBN");
    }
}