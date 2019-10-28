package com.testingtigers.domain;

import java.util.UUID;

public class Book {

    private String isbn;
    private final String uniqueId;
    private String title;
    private String authorID;
    private String summary;

    public Book(String isbn, String title, String authorID, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = UUID.randomUUID().toString();
    }

    public Book( String title, String authorID, String summary) {
        this.isbn = "BLANK";
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = "BLANK";
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
