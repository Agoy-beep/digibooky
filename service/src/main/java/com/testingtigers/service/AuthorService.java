package com.testingtigers.service;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.AuthorMapper;
import com.testingtigers.domain.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorDto findSpecificAuthorIfNotFoundCreateNewAuthor (String authorLastName){
        Collection<Author> listOfAuthors = authorRepository.getAll();
        for(Author author : listOfAuthors){
            if(authorLastName.equals(author.getLastName())){
                return authorMapper.mapToDto(author);
            }
        }

        //TODO MUST THIS BE BLANK?
        Author newAuthor = new Author("BLANK", authorLastName);
        authorRepository.addAuthor(newAuthor);
        return authorMapper.mapToDto(newAuthor);
    }
}
