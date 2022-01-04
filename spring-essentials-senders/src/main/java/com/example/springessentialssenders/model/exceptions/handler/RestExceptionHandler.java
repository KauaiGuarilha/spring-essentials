package com.example.springessentialssenders.model.exceptions.handler;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.ErrorDTO;
import com.example.springessentialssenders.model.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import com.example.springessentialssenders.model.exceptions.UsernameAlreadyInUseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {

        log.error("Resource Not Found", resourceNotFoundException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.NOT_IDENTIFIED.getCode())
                .message(resourceNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<?> handlerUsernameAlreadyInUseException(
            UsernameAlreadyInUseException usernameAlreadyInUseException) {

        log.error("Username is already using, try another one.", usernameAlreadyInUseException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.USERNAME_ALREADY_USING.getCode())
                .message(usernameAlreadyInUseException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlerPropertyReferenceException(
            PropertyReferenceException resourceNotFoundException) {

        log.error("Property Reference Error", resourceNotFoundException);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.NOT_IDENTIFIED.getCode())
                .message(resourceNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException resourceNotFoundException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<FieldError> fieldErrors =
                resourceNotFoundException.getBindingResult().getFieldErrors();
        String fields =
                fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessage =
                fieldErrors.stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));

        ErrorDTO errorDTO =
                ErrorDTO.builder().field(fields).message(fieldMessage).build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.error("Generic error in request processing", ex);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(EValidation.NOT_IDENTIFIED.getCode())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, headers, HttpStatus.BAD_REQUEST);
    }
}
