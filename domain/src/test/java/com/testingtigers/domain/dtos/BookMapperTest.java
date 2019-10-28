package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private BookMapper bookMapper = new BookMapper();
    @Test
    void mapToBook() {
        //GIVEN

        CreateBookDto bookDto = new CreateBookDto();
        bookDto.setSummary("Seum");
        bookDto.setTitle("Titles");
        bookDto.setIsbn("42");
        bookDto.setAuthorLastName("5");
        //WHEN
        Book book = bookMapper.mapToBook(bookDto);
        //THEN
        assertThat(book.getAuthorID()).isEqualTo("5");
        assertThat(book.getTitle()).isEqualTo("Titles");
        assertThat(book.getIsbn()).isEqualTo("42");

    }

    @Test
    void mapToDto() {
        //GIVEN
        Book book = new Book("ISBN", "TITLE", "AUTHORID", "SUMMARY");
        //WHEN
        BookDto bookDto = bookMapper.mapToDto(book);
        //THEN
        assertThat(bookDto.getUniqueId()).isEqualTo(book.getId());
        assertThat(bookDto.getAuthorID()).isEqualTo(book.getAuthorID());
        assertThat(bookDto.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(bookDto.getSummary()).isEqualTo(book.getSummary());
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
    }
}