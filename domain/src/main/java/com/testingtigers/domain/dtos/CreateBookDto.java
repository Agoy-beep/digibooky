package com.testingtigers.domain.dtos;

public class CreateBookDto {

    private String isbn;
    private String title;
    private String authorLastName;
    private String summary;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorLastName() {
        return authorLastName;
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

    public CreateBookDto setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
        return this;
    }

    public CreateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }
}
