package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookDataBaseDummy {

    private final Map<String, Book> bookDB;


    public BookDataBaseDummy() {
        this.bookDB = new HashMap<>();
    }

    public Map<String, Book> getBookDB() {
        return bookDB;
    }
}
