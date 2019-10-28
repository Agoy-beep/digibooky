package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFound extends ResponseStatusException {

    public BookNotFound(HttpStatus status, String message) {
        super(status, message);
    }
}
