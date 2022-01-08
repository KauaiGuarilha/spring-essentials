package com.example.springessentialssenders.model.exceptions.handler;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.ErrorDTO;
import com.example.springessentialssenders.model.dto.ErrorsDTO;
import com.example.springessentialssenders.model.exceptions.StudentNotFoundException;
import com.example.springessentialssenders.model.exceptions.UUIDNotFoundException;
import com.example.springessentialssenders.model.exceptions.UserNotFoundException;
import com.example.springessentialssenders.model.exceptions.UsernameAlreadyInUseException;
import com.example.springessentialssenders.model.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ValidatorMethodExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<?> handlerUUIDNotFoundException(
            UsernameAlreadyInUseException usernameAlreadyInUseException) {

        log.error("Username is already using, try another one.", usernameAlreadyInUseException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.USERNAME_ALREADY_USING.getCode())
                .message(usernameAlreadyInUseException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handlerUUIDNotFoundException(
            UserNotFoundException userNotFoundException) {

        log.error("User not found for ID.", userNotFoundException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.USER_NOT_FOUND_FOR_ID.getCode())
                .message(userNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UUIDNotFoundException.class)
    public ResponseEntity<?> handlerUUIDNotFoundException(
            UUIDNotFoundException uuidNotFoundException) {

        log.error("UUID does not exist or null.", uuidNotFoundException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.UUID_NOT_FOUND.getCode())
                .message(uuidNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

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