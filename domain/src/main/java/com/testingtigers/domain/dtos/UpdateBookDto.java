package com.testingtigers.domain.dtos;

public class UpdateBookDto {

    //private String isbn;
    private String title;
    private String authorID;
    private String summary;

    /*public String getIsbn() {
        return isbn;
    }*/

    public String getTitle() {
        return title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getSummary() {
        return summary;
    }

    public UpdateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdateBookDto setAuthorID(String authorID) {
        this.authorID = authorID;
        return this;
    }

    public UpdateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }
}
