package com.adam.commoninterestsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriptionDuplicationException extends RuntimeException {
    public SubscriptionDuplicationException(String message) {
        super(message);
    }
}
