package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LentBadFormError extends ResponseStatusException {
    public LentBadFormError(HttpStatus status, String reason) {
        super(status, reason);
    }
}
