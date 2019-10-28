package com.testingtigers.domain.repositories;

import com.testingtigers.domain.Author;
import com.testingtigers.domain.AuthorAndBookID;
import com.testingtigers.domain.Book;
import com.testingtigers.domain.dtos.AuthorDto;
import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.BookMapper;
import com.yevdo.jwildcard.JWildcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.testingtigers.domain.exceptions.*;
import org.springframework.web.server.ResponseStatusException;

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

    public BookDto deleteBookFromDatabaseByID(String IDToDelete) {
        List<Book> bookList = new ArrayList<>(databaseBooks.getBookDB().values());
        for (Book bookToExam : bookList) {
            if (bookToExam.getId().equals(IDToDelete)) {
                bookToExam.setSoftDelete(true);
                return bookMapper.mapToDto(bookToExam);
            }
        }
        throw new BookNotFound(HttpStatus.BAD_REQUEST, "Book with ID "+IDToDelete+ " not found to soft-delete");
    }
    /*
    "isbn": "123-456-danny",
        "uniqueId": "326eeb5a-71f6-4641-8056-e4c44be24fe4",
        "title": "DannyTitle",
        "authorID": "ba248be4-af17-422b-b188-fede5b27e83d",
        "summary": "DannySummery"
     */

    public BookDto undeleteBookFromDatabaseByID(String IDToUnDelete) {
        List<Book> bookList = new ArrayList<>(databaseBooks.getBookDB().values());

        for (Book bookToExam : bookList) {
            if (bookToExam.getId().equals(IDToUnDelete)) {
                bookToExam.setSoftDelete(false);
                return bookMapper.mapToDto(bookToExam);
            }
        }
        throw new BookNotFound(HttpStatus.BAD_REQUEST, "Book with ID "+IDToUnDelete+ " not found to un -soft-delete");
    }

    public Book getById(String id) {
        Book foundBook = databaseBooks.getBookDB().get(id);
        if (foundBook == null) {
            throw new BookNotFound(HttpStatus.BAD_REQUEST,"Book not found");
        } else {
            if (foundBook.isSoftDeleted()) { throw new BookNotFound(HttpStatus.BAD_REQUEST, "Book is soft-deleted"); }
            return foundBook;
        }
    }

    public List<Book> getAllBooks() {
        return filterOutSoftDeletedBooks(new ArrayList<>(databaseBooks.getBookDB().values()));
    }

    public List<Book> filterOutSoftDeletedBooks(List<Book> listToFilter) {
        List<Book> result = new ArrayList<>();
        for (Book bookToExam : listToFilter) {
            if (!bookToExam.isSoftDeleted()) {
                result.add(bookToExam);
            }
        }
        return result;
    }

    private List<Book> getBookDBAsList() {
        // filter ok for soft delete
        return filterOutSoftDeletedBooks(new ArrayList<Book>(databaseBooks.getBookDB().values()));
    }

    public List<BookDto> getBookByISBN(String ISBNToFind) {
        // filter ok for soft delete
        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();
        for (Book bookToExam : filterOutSoftDeletedBooks(getBookDBAsList())) {
            if (JWildcard.matches(ISBNToFind, bookToExam.getIsbn())) {

                resultingBooks.add(bookMapper.mapToDto(bookToExam));
            }
        }
        return resultingBooks;
    }

    public List<BookDto> getBookByTitle(String titleToFind) {
        // filter ok for soft delete
        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();
        for (Book bookToExam : filterOutSoftDeletedBooks(getBookDBAsList())) {
            if (JWildcard.matches(titleToFind, bookToExam.getTitle())) {
                resultingBooks.add(bookMapper.mapToDto(bookToExam));
            }
        }
        return resultingBooks;
    }

    public List<BookDto> getBookByAuthor(String firstNameToFind, String lastNameToFind, AuthorRepository authors) {
        // filter ok for soft delete
        // see https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
        List<BookDto> resultingBooks = new ArrayList<>();
        List<AuthorAndBookID> allAuthorsWithBookID = new ArrayList<>();

        for (Book bookToExam : filterOutSoftDeletedBooks(getBookDBAsList())) {
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