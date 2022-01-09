package com.example.springessentialssenders.model.exceptions.handler;

import com.example.springessentialssenders.model.dto.ErrorsDTO;
import com.example.springessentialssenders.model.exceptions.FeignDecoderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class FeignDecoderExceptionHandler {

    @ExceptionHandler(FeignDecoderException.class)
    protected ErrorsDTO feignDecoderExceptionHandler(
            FeignDecoderException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        return e.getErrorsDTO();
    }
}
