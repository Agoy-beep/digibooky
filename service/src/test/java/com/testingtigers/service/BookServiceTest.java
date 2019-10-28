package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.repositories.BookDataBaseDummy;
import com.testingtigers.domain.repositories.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest {

    BookDataBaseDummy dumdum = new BookDataBaseDummy();
    BookRepository repo = new BookRepository(dumdum);
    BookService bookService = new BookService(repo);
    BookMapper bookMapper = new BookMapper();

    @Test
    void makeListOfBookDtos() {
        //GIVEN
        List<BookDto> bookDtos = new ArrayList<>();

        //WHEN
        for(Book book : repo.getAllBooks()){
            bookDtos.add(bookMapper.mapToDto(book));
        }
        //THEN
        assertThat(bookDtos.size()).isEqualTo(repo.getAllBooks().size());
    }

    @Test
    void returnSpecificBookBasedOnId() {
        //GIVEN
        Book book = new Book ("ISBN", "TITLE", "AUTHOR2", "SUMS");
        repo.addBookToDataBase(book);
        //WHEN
        BookDto bookDto = bookMapper.mapToDto(repo.getById(book.getId()));
        //THEN
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void createBook() {
        //GIVEN

        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setIsbn("ISBN");
        createBookDto.setAuthorLastName("A4");
        createBookDto.setTitle("Title");
        createBookDto.setSummary("SUM");
        //WHEN
        BookDto newBookDto = bookService.registerBookAndReturnDto(createBookDto);
        //THEN
        assertThat(newBookDto.getTitle()).isEqualTo("Title");
        assertThat(newBookDto.getUniqueId()).isNotBlank();
        assertThat(newBookDto.getAuthorID()).isEqualTo("A4");
        assertThat(newBookDto.getIsbn()).isEqualTo("ISBN");
    }
}