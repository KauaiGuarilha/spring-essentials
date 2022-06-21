package com.example.springessentialssenders.model.exceptions;

import com.example.springessentialssenders.model.domain.EValidation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FirebaseStorageException extends ExceptionAbstract {

    @Getter
    private String description;

    public FirebaseStorageException(String message) {
        super(message);
        this.description = message;
    }

    public FirebaseStorageException(EValidation validation, String... params) {
        super(validation, params);
    }
}
