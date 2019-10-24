package com.testingtigers.domain;

import java.util.UUID;

public class Book {

    private final String isbn;
    private final String uniqueId;
    private final String title;
    private final String authorID;
    private final String summary;

    public Book(String isbn, String title, String authorID, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = UUID.randomUUID().toString();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getSummary() {
        return summary;
    }

    public String getId() {
        return uniqueId;
    }
}
