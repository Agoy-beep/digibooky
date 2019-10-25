package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.yevdo.jwildcard.JWildcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookDataBaseDummy {

    private final Map<String, Book> bookDB;

    public BookDataBaseDummy() {
        this.bookDB = new HashMap<>();
        bookDB.put("DannyTest",new Book("123-456-danny" , "DannyTitle", "DannyAuthorID","DannySummery"));
    }

    public Map<String, Book> getBookDB() {
        return bookDB;
    }

}
