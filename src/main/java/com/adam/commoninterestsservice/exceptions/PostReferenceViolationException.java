package com.adam.commoninterestsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PostReferenceViolationException extends RuntimeException {
    public PostReferenceViolationException(String message) {
        super(message);
    }
}
