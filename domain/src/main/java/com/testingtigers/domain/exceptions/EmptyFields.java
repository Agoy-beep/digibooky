package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyFields extends ResponseStatusException {

    public EmptyFields(HttpStatus status, String reason) {
        super(status, reason);
    }
}
