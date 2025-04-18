package com.switchfully.digibooky.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServorErrorException extends RuntimeException {
    public InternalServorErrorException(String message) {
        super(message);
    }
}
