package com.example.springessentialssenders.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameAlreadyInUseException extends RuntimeException {

    @Getter
    private String description;

    public UsernameAlreadyInUseException(String message) {
        super(message);
        this.description = message;
    }
}
