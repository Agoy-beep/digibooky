package com.testingtigers.service;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.AuthorMapper;
import com.testingtigers.domain.repositories.AuthorRepository;
//import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    AuthorRepository authorRepository = new AuthorRepository();
    AuthorService authorService = new AuthorService(authorRepository);
    AuthorMapper authorMapper = new AuthorMapper();

    @Test
    void findSpecificAuthorIfNotFoundCreateNewAuthor(){
        String authorLastName = "Willis";

        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(authorLastName);

        Assertions.assertThat(authorRepository.getById(authorDto.getAuthorID()).getAuthorID()).isNotBlank();
        assertThat(authorRepository.getById(authorDto.getAuthorID()).getLastName()).isEqualTo("Willis");
    }

    @Test
    void findSpecificAuthor(){
        Author newAuthor = new Author("Bob", "Willis");

        authorRepository.addAuthor(newAuthor);

        AuthorDto authorDto = authorService.findSpecificAuthorIfNotFoundCreateNewAuthor(newAuthor.getLastName());

        Assertions.assertThat(authorRepository.getById(authorDto.getAuthorID()).getLastName()).isEqualTo("Willis");
        assertThat(authorRepository.getAll().contains(newAuthor));
        assertThat(authorRepository.getById(authorDto.getAuthorID()).getFirstName()).isEqualTo(newAuthor.getFirstName());
    }

}