package com.testingtigers.domain.dtos;

import com.testingtigers.domain.Author;

public class CreateBookDto {

    private String isbn;
    private String title;
    private String authorID;
    private String summary;

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

    public CreateBookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public CreateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateBookDto setAuthorID(String authorID) {
        this.authorID = authorID;
        return this;
    }

    public CreateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }
}
