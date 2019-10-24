package com.testingtigers.domain.repositories;


import com.testingtigers.domain.Book;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookRepository {

    private final Map<String, Book> bookDB;

    public BookRepository() {
        this.bookDB = new HashMap<>();
    }

    public void addBookToDataBase(Book bookToAdd){
        bookDB.put(bookToAdd.getId(), bookToAdd);
    }
    public void deleteBookFromDataBase(Book bookToDelete){
        bookDB.remove(bookToDelete.getId(), bookToDelete);
    }

    public Book getById(String id){
        Book foundBook = bookDB.get(id);
        if(foundBook == null){
            throw new IllegalArgumentException();
        }
        else{
            return foundBook;
        }
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(bookDB.values());
    }


}
