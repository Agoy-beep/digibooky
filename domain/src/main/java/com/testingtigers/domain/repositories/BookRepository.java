package com.testingtigers.domain.repositories;


import com.testingtigers.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepository {

    private final BookDataBaseDummy database;

    @Autowired
    public BookRepository(BookDataBaseDummy database) {
        this.database = database;
    }

    public void addBookToDataBase(Book bookToAdd){
        database.getBookDB().put(bookToAdd.getId(), bookToAdd);
    }
    public void deleteBookFromDataBase(Book bookToDelete){
        database.getBookDB().remove(bookToDelete.getId(), bookToDelete);
    }

    public Book getById(String id){
        Book foundBook = database.getBookDB().get(id);
        if(foundBook == null){
            throw new IllegalArgumentException();
        }
        else{
            return foundBook;
        }
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(database.getBookDB().values());
    }


}
