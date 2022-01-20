package com.example.springessentialsconsumers.model.exceptions;

import com.example.springessentialsconsumers.model.domain.EValidation;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends ExceptionAbstract {

    @Getter private String description;

    public StudentNotFoundException(String message) {
        super(message);
        this.description = message;
    }

    public StudentNotFoundException(EValidation validation, String... params) {
        super(validation, params);
    }
}
