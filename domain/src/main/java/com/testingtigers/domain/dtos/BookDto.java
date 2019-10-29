package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Author;

public class BookDto {

    private String isbn;
    private String uniqueId;
    private String title;
    private String authorID;
    private String summary;

    @Override
    public String toString() {
        return  "isbn='" + isbn + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", title='" + title + '\'' +
                ", authorID='" + authorID + '\'' +
                ", summary='" + summary + '\'';
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUniqueId() {
        return uniqueId;
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

    public BookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookDto setAuthorID(String authorID) {
        this.authorID = authorID;
        return this;
    }

    public BookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public BookDto setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }
}
