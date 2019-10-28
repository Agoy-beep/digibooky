package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.users.Member;
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
            throw new IllegalArgumentException();
        }
        else{
            return foundAuthor;
        }
    }
}
