package com.testingtigers.domain;

import com.testingtigers.domain.repositories.BookDataBaseDummy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class DatabaseConfig {

    private Author testAuthor = new Author("Arthur", "Dent");


    @Bean("productionBookDatabase")
    @Profile("test")
    public BookDataBaseDummy bookDataBaseDummy() {
        return new BookDataBaseDummy();
    }

    private HashMap<String, Book> generateInitialData() {

       HashMap<String, Book> testDB =  new HashMap<>();
       testDB.put(UUID.randomUUID().toString(), new Book("97884933179", "thisBook",
                testAuthor.getAuthorID(), "aBookSummary" ));
       return testDB;
    }

}
