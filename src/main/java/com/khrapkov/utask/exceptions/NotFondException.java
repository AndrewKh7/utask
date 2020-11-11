package com.khrapkov.utask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFondException extends RuntimeException {

    public NotFondException(String message){
        super(message);
    }

}
