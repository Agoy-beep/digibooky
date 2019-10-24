package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Author;

public class BookDto {

    private String isbn;
    private String uniqueId;
    private String title;
    private String authorID;
    private String summary;

    public BookDto(String isbn, String uniqueId, String title, String authorID, String summary) {
        this.isbn = isbn;
        this.uniqueId = uniqueId;
        this.title = title;
        this.authorID = authorID;
        this.summary = summary;
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

    public void setIsbn(String isbn) {
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
