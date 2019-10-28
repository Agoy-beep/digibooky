package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;

public class AuthorMapper {
    public Author mapToBoo(CreateAuthorDto createAuthorDto) {
        return new Author(createAuthorDto.getFirstName(), createAuthorDto.getLastName());
    }
    public  AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setFirstName(author.getFirstName());
        authorDto.setLastName(author.getLastName());
        authorDto.setAuthorId(author.getAuthorID());

        return authorDto;
    }
}
