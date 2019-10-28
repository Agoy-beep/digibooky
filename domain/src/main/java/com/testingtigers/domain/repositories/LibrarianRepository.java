package com.testingtigers.domain.repositories;

import com.testingtigers.domain.users.Librarian;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LibrarianRepository {

    private final Map<String, Librarian> librarians;

    public LibrarianRepository() {
        this.librarians = new HashMap<>();
    }

    public Collection<Librarian> getAll() {
        return librarians.values();
    }

    public Librarian addLibrarian(Librarian librarianToAdd) {
        librarians.put(librarianToAdd.getId(), librarianToAdd);
        return librarianToAdd;
    }
}
