package com.example.springessentials.model.exceptions;

import lombok.Getter;

@Getter
public class EssentialsRuntimeException extends RuntimeException {

    public EssentialsRuntimeException(String msg) {
        super(msg);
    }

    public EssentialsRuntimeException(Throwable causa) {
        super(causa);
    }

    public EssentialsRuntimeException(String msg, Throwable causa) {
        super(msg, causa);
    }
}
