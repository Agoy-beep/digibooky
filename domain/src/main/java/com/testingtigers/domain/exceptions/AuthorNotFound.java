package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorNotFound extends ResponseStatusException {

    public AuthorNotFound(HttpStatus status, String reason) {
        super(status, reason);
    }
}
