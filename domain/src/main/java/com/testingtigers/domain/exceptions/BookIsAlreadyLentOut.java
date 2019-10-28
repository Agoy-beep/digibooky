package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookIsAlreadyLentOut extends ResponseStatusException {
    public BookIsAlreadyLentOut(HttpStatus status, String reason) {
        super(status, reason);
    }
}
