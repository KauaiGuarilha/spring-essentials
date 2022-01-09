package com.example.springessentialssenders.model.exceptions.handler;

import com.example.springessentialssenders.model.dto.ErrorsDTO;
import com.example.springessentialssenders.model.utils.FeignExceptionUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class FeignExceptionHandler {

    @Autowired FeignExceptionUtils feignExceptionUtils;

    @ExceptionHandler(FeignException.class)
    protected ErrorsDTO handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status() != -1 ? e.status() : HttpStatus.SC_INTERNAL_SERVER_ERROR);
        return feignExceptionUtils.getErrorsDTO(e);
    }
}
