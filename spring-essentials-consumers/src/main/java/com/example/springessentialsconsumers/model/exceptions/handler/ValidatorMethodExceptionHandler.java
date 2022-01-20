package com.example.springessentialsconsumers.model.exceptions.handler;

import com.example.springessentialsconsumers.model.domain.EValidation;
import com.example.springessentialsconsumers.model.dto.ErrorDTO;
import com.example.springessentialsconsumers.model.exceptions.StudentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidatorMethodExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handlerStudentNotFoundException(
            StudentNotFoundException studentNotFoundException) {

        log.error("User not found for ID.", studentNotFoundException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.STUDENT_NOT_FOUND_FOR_ID.getCode())
                .message(studentNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}