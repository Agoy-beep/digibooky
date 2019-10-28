package com.testingtigers.domain;

public class BookBuilder {


    public Book createBookWithoutSummary( String isbn, String title, String authorID) {
        Book newBook = new Book(isbn, title, authorID, "BLANK");
        newBook.setIsbn(isbn);
        return newBook;
    }
}
