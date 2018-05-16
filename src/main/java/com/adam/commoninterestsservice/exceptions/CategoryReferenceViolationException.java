package com.adam.commoninterestsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryReferenceViolationException extends RuntimeException {
    public CategoryReferenceViolationException(String message) {
        super(message);
    }
}
