package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void mapToBook() {
        //GIVEN
        Mapper mapper = new Mapper();

        CreateBookDto bookDto = new CreateBookDto();
        bookDto.setSummary("Seum");
        bookDto.setTitle("Titles");
        bookDto.setIsbn("42");
        bookDto.setAuthorID("5");
        //WHEN
        Book book = mapper.mapToBook(bookDto);
        //THEN
        Assertions.assertThat(book.getAuthorID()).isEqualTo("5");
        Assertions.assertThat(book.getAuthorID()).isEqualTo("5");
        Assertions.assertThat(book.getAuthorID()).isEqualTo("5");

    }

    @Test
    void mapToDto() {
        //GIVEN
        //WHEN
        //THEN
    }
}