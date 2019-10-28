package com.testingtigers.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MemberNotFound extends ResponseStatusException {
    public MemberNotFound(HttpStatus status, String reason) {
        super(status, reason);
    }
}
