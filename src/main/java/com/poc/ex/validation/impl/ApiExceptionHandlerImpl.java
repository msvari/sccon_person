package com.poc.ex.validation.impl;

import com.poc.ex.model.dto.ErrorDTO;
import com.poc.ex.validation.ApiExceptionHandler;
import com.poc.ex.validation.exception.PersonAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.poc.ex.validation.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandlerImpl implements ApiExceptionHandler {

    @Override
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> genericException(Exception ex) {
        log.error("state=generic-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorDTO> notFoundException(RuntimeException ex) {
        log.error("state=not-found-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> personAlreadyExistsException(RuntimeException ex) {
        log.error("state=person-already-exists-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

}
