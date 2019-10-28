package com.testingtigers.domain;



import java.util.UUID;

public class Book {

    private String isbn;
    private final String uniqueId;

    private String title;
    private String authorID;
    private String summary;
    private boolean softDeleted; // DO NOT MAKE FINAL

    public Book(String isbn, String title, String authorID, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = UUID.randomUUID().toString();
        setSoftDelete(false);
    }

    //to update a book
    public Book( String title, String authorID, String summary) {
        this.isbn = "BLANK";
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
        this.uniqueId = "BLANK";
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public boolean setSoftDelete(boolean value) {
        return softDeleted = value;
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
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
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
