package com.example.springessentialsconsumers.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    @Getter private String description;

    public ResourceNotFoundException(String message) {
        super(message);
        this.description = message;
    }

    public ResourceNotFoundException(Throwable throwable) {
        super(throwable);
        this.description = throwable.getMessage();

        if (throwable instanceof  ResourceNotFoundException)
            this.description = ((ResourceNotFoundException) throwable).getDescription();
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
        this.description = message;
    }
}
