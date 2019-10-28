package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.exceptions.AuthorNotFound;
import com.testingtigers.domain.users.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthorRepository {
    private final Map<String, Author> authors;


    public AuthorRepository() {
        authors = new HashMap<>();
    }

    public Author addAuthor(Author authorToAdd) {
        authors.put(authorToAdd.getAuthorID(), authorToAdd);
        return authorToAdd;
    }

    public Collection<Author> getAll() {
        return authors.values();
    }
    public List<Author> getAuthorDBAsList() {
        return new ArrayList<Author>(authors.values());
    }

    public Author getById(String id){
        Author foundAuthor = authors.get(id);
        if(foundAuthor == null){
            throw new AuthorNotFound(HttpStatus.BAD_REQUEST, "Author with ID:" + id + " is not in our database!");
        }
        else{
            return foundAuthor;
        }
    }
}
