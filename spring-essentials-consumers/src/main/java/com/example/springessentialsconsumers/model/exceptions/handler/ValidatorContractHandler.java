package com.example.springessentialsconsumers.model.exceptions.handler;

import com.example.springessentialsconsumers.model.dto.ErrorDTO;
import com.example.springessentialsconsumers.model.dto.ErrorsDTO;
import com.example.springessentialsconsumers.model.exceptions.ExceptionAbstract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ValidatorContractHandler {

    @ExceptionHandler(ExceptionAbstract.class)
    protected ResponseEntity<Object> validatorHandle(ExceptionAbstract e) {
        List<ErrorDTO> errors = new ArrayList<>();

        if (Objects.isNull(e.getMsgs()) || e.getMsgs().size() == 0) {
            errors.add(ErrorDTO.builder().code(e.getCode()).message(e.getMessage()).build());
        } else {
            e.getMsgs()
                    .forEach(
                            m ->
                                    errors.add(
                                            ErrorDTO.builder()
                                                    .code(e.getCode())
                                                    .message(m)
                                                    .build()));
        }

        return new ResponseEntity<>(
                ErrorsDTO.builder().errors(errors).build(), HttpStatus.BAD_REQUEST);
    }
}
