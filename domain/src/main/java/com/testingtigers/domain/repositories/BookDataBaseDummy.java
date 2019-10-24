package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Book;
import org.springframework.stereotype.Component;

import java.util.Map;

public class BookDataBaseDummy {

    private final Map<String, Book> bookDB;

    public BookDataBaseDummy(Map<String, Book> bookDB) {
        this.bookDB = bookDB;
    }

    public Map<String, Book> getBookDB() {
        return bookDB;
    }
}
