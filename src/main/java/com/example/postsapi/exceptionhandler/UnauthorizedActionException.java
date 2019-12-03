package com.example.postsapi.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedActionException extends Exception {
    private static final long serialVersionID = 1l;
    public UnauthorizedActionException(HttpStatus httpStatus, String message) {
        super(message);
    }
}
