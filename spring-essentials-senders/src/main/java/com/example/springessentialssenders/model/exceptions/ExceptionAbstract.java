package com.example.springessentialssenders.model.exceptions;

import com.example.springessentialssenders.model.domain.EValidation;
import lombok.Getter;

public abstract class ExceptionAbstract extends RuntimeException {

    private EValidation validation;

    @Getter
    private String[] params;

    public Integer getCode() {
        return this.validation.getCode();
    }

    public String getMensagem() {
        return this.validation.getDescription(this.params);
    }

    public ExceptionAbstract(EValidation validation) {
        super(validation.getDescription());
        this.validation = validation;
    }

    public ExceptionAbstract(EValidation validation, String... params) {
        super(validation.getDescription());
        this.validation = validation;
        this.params = params;
    }

    public ExceptionAbstract(EValidation validation, Throwable cause) {
        super(validation.getDescription(), cause);
        this.validation = validation;
    }

    public ExceptionAbstract(String validation) {
        super(validation);
    }
}
