package com.testingtigers.domain;

import groovy.transform.ToString;

import java.util.UUID;

public class Book {

    private String isbn;
    private final String uniqueId;

    private final String title;
    private final String authorID;
    private final String summary;
    private boolean softDeleted; // DO NOT MAKE FINAL



    public Book(String isbn, String title, String authorID, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = UUID.randomUUID().toString();
        setSoftDelete(false);
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public boolean setSoftDelete(boolean value) {
        return softDeleted = value;
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


    @Override
    public String toString() {
        // for test purposes
        return "ToString : " + getId() + " " + getIsbn() + " " + getTitle();
      
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
