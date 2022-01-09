package com.example.springessentialssenders.model.exceptions;

import com.example.springessentialssenders.model.dto.ErrorsDTO;
import lombok.Getter;

public class FeignDecoderException extends RuntimeException {

    @Getter private ErrorsDTO errorsDTO;

    public FeignDecoderException(ErrorsDTO erros) {
        this.errorsDTO = erros;
    }
}
