package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.AuthorAndBookID;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.yevdo.jwildcard.JWildcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepository {

    private final BookDataBaseDummy databaseBooks;
    private final BookMapper bookMapper = new BookMapper();

    @Autowired
    public BookRepository(BookDataBaseDummy databaseBooks) {
        this.databaseBooks = databaseBooks;
    }

    public void addBookToDataBase(Book bookToAdd) {
        databaseBooks.getBookDB().put(bookToAdd.getId(), bookToAdd);
    }

    public void deleteBookFromDataBase(Book bookToDelete) {
        databaseBooks.getBookDB().remove(bookToDelete.getId(), bookToDelete);
    }

    public Book getById(String id) {
        Book foundBook = databaseBooks.getBookDB().get(id);
        if (foundBook == null) {
            throw new IllegalArgumentException();
        } else {
            return foundBook;
        }
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(databaseBooks.getBookDB().values());
    }

    private List<Book> getBookDBAsList() {
        return new ArrayList<Book>(databaseBooks.getBookDB().values());
    }

    public List<BookDto> getBookByISBN(String ISBNToFind) {

        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();

        for (Book bookToExam : getBookDBAsList()) {
            if (JWildcard.matches(ISBNToFind, bookToExam.getIsbn())) {

                resultingBooks.add(bookMapper.mapToDto(bookToExam));
            }
        }
        return resultingBooks;
    }

    public List<BookDto> getBookByTitle(String titleToFind) {
        List<BookDto> resultingBooks = new ArrayList<>();

        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching

        for (Book bookToExam : getBookDBAsList()) {
            if (JWildcard.matches(titleToFind, bookToExam.getTitle())) {
                resultingBooks.add(bookMapper.mapToDto(bookToExam));
            }
        }
        return resultingBooks;
    }

    public List<BookDto> getBookByAuthor(String firstNameToFind, String lastNameToFind, AuthorRepository authors) {
        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();

        List<AuthorAndBookID> allAuthorsWithBookID = new ArrayList<>();

        for (Book bookToExam : getBookDBAsList()) {
            // only authors we have and that have a book will be added
            allAuthorsWithBookID.add(
                    new AuthorAndBookID(bookToExam.getAuthorID(), bookToExam.getId()));
        }

        for (AuthorAndBookID authorAndBookIDToExam : allAuthorsWithBookID) {
            Book bookToExam = getById(authorAndBookIDToExam.getBookID());
            Author authorToExam = authors.getById(authorAndBookIDToExam.getAuthorID());
            if ((
                    JWildcard.matches(firstNameToFind, authorToExam.getFirstName()) ||
                            (JWildcard.matches(lastNameToFind, authorToExam.getLastName())))) {
                resultingBooks.add(bookMapper.mapToDto(bookToExam));
            }
            return resultingBooks;
        }
        return resultingBooks;
    }

}