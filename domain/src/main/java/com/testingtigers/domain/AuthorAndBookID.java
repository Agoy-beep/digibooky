package com.testingtigers.domain;

public class AuthorAndBookID {
    private final String bookID;

    public String getBookID() {
        return bookID;
    }

    public String getAuthorID() {
        return authorID;
    }

    private final String authorID;

    public AuthorAndBookID(String authorID, String bookID) {
        this.authorID = authorID;
        this.bookID = bookID;
    }
}
