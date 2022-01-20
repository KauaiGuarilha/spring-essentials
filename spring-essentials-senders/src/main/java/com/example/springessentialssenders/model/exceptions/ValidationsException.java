package com.example.springessentialssenders.model.exceptions;

import lombok.Getter;

@Getter
public class ValidationsException extends ExceptionAbstract {

    public ValidationsException(Integer codigo, String mensagem) {
        super(codigo, mensagem);
    }
}
