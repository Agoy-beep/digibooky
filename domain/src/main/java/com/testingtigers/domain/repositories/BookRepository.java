package com.testingtigers.domain.repositories;


import com.testingtigers.domain.Author;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.yevdo.jwildcard.JWildcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepository {

    private final BookDataBaseDummy database;
    private final BookMapper bookMapper = new BookMapper();

    @Autowired
    public BookRepository(BookDataBaseDummy database) {
        this.database = database;
    }

    public void addBookToDataBase(Book bookToAdd){
        database.getBookDB().put(bookToAdd.getId(), bookToAdd);
    }
    public void deleteBookFromDataBase(Book bookToDelete){
        database.getBookDB().remove(bookToDelete.getId(), bookToDelete);
    }

    public Book getById(String id){
        Book foundBook = database.getBookDB().get(id);
        if(foundBook == null){
            throw new IllegalArgumentException();
        }
        else{
            return foundBook;
        }
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(database.getBookDB().values());
    }


    private List<Book> getBookDBAsList() {
        return new ArrayList<Book>(database.getBookDB().values());
    }

    public List<BookDto> getBookByISBN(String ISBNToFind) {

        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();

        for (Book bookToExam : getBookDBAsList()) {
            if (JWildcard.matches(ISBNToFind, bookToExam.getIsbn())) {

                resultingBooks.add(bookMapper.mapToDto( bookToExam));
            }
        }
        return resultingBooks;
    }

    public List<BookDto> getBookByTitle(String titleToFind) {
        List<BookDto> resultingBooks = new ArrayList<>();

        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching

        for (Book bookToExam : getBookDBAsList()) {
            if (JWildcard.matches(titleToFind, bookToExam.getTitle())) {
                resultingBooks.add(bookMapper.mapToDto ( bookToExam));
            }
        }
        return resultingBooks;
    }
    public List<BookDto> getBookByAuthor(String authorToFind,AuthorRepository authors) {
        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();


        List<Author> allAuthors = authors.getAuthorDBAsList();

        for (Book bookToExam : getBookDBAsList()) {
            allAuthors.add(authors.getById( bookToExam.getAuthorID()));
        }

        for (Book bookToExam : getBookDBAsList()) {

        }





        return resultingBooks;
    }

}
