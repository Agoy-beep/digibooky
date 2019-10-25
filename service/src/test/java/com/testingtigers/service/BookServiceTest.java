package com.testingtigers.service;

import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.repositories.BookDataBaseDummy;
import com.testingtigers.domain.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    Map<String, Book> db = new HashMap<>();
    BookDataBaseDummy dumdum = new BookDataBaseDummy(db);
    BookRepository repo = new BookRepository(dumdum);
    BookService bookService = new BookService(repo);

    @Test
    void makeListOfBookDtos() {
        //GIVEN

        //WHEN
        //THEN
    }

    @Test
    void returnSpecificBookBasedOnId() {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    void createBook() {
        //GIVEN

        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setIsbn("ISBN");
        createBookDto.setAuthorID("A4");
        createBookDto.setTitle("Title");
        createBookDto.setSummary("SUM");
        //WHEN
        BookDto newBookDto = bookService.createBook(createBookDto);
        //THEN
        assertThat(newBookDto.getTitle()).isEqualTo("Title");
        assertThat(newBookDto.getUniqueId()).isNotBlank();
        assertThat(newBookDto.getAuthorID()).isEqualTo("A4");
        assertThat(newBookDto.getIsbn()).isEqualTo("ISBN");
    }
}