package com.example.springessentialssenders.model.exceptions;

import com.example.springessentialssenders.model.domain.EValidation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameAlreadyInUseException extends ExceptionAbstract {

    @Getter private String description;

    public UsernameAlreadyInUseException(String message) {
        super(message);
        this.description = message;
    }

    public UsernameAlreadyInUseException(EValidation validation, String... params) {
        super(validation, params);
    }
}
