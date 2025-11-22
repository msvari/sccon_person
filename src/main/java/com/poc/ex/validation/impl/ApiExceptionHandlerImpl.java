package com.poc.ex.validation.impl;

import com.poc.ex.model.dto.ErrorDTO;
import com.poc.ex.model.enumeration.ErrorValidation;
import com.poc.ex.validation.ApiExceptionHandler;
import com.poc.ex.validation.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
                .code(ErrorValidation.INVALID_INTERNAL_SERVER_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(PersonIllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> personIllegalArgumentException(RuntimeException ex) {
        log.error("state=person-illegal-argument-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_PERSON_ILLEGAL_ARGUMENT_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(PersonCalculateAgeException.class)
    public ResponseEntity<ErrorDTO> personCalculateAgeException(RuntimeException ex) {
        log.error("state=person-calculate-age-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_CALCULATE_AGE_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(PersonCalculateSalaryException.class)
    public ResponseEntity<ErrorDTO> personCalculateSalaryException(RuntimeException ex) {
        log.error("state=person-calculate-salary-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_CALCULATE_SALARY_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorDTO> personNotFoundException(RuntimeException ex) {
        log.error("state=person-not-found-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_PERSON_NOT_FOUND_ERROR.getCode())
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
                .code(ErrorValidation.INVALID_PERSON_ALREADY_EXISTS_ERROR.getCode())
                .status(HttpStatus.CONFLICT.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @Override
    @ExceptionHandler(DatabaseSaveException.class)
    public ResponseEntity<ErrorDTO> databaseSaveException(Exception ex) {
        log.error("state=database-save-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_DATABASE_SAVE_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(DatabaseUpdateAllException.class)
    public ResponseEntity<ErrorDTO> databaseUpdateAllException(Exception ex) {
        log.error("state=database-update-all-fields-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_DATABASE_UPDATE_ALL_FIELDS_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(DatabaseUpdateSomeException.class)
    public ResponseEntity<ErrorDTO> databaseUpdateSomeException(Exception ex) {
        log.error("state=database-update-some-fields-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_DATABASE_UPDATE_SOME_FIELDS_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(DatabaseFindException.class)
    public ResponseEntity<ErrorDTO> databaseFindException(Exception ex) {
        log.error("state=database-find-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_DATABASE_FIND_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @ExceptionHandler(DatabaseDeleteException.class)
    public ResponseEntity<ErrorDTO> databaseDeleteException(Exception ex) {
        log.error("state=database-delete-exception", ex);
        ErrorDTO apiError = ErrorDTO
                .builder()
                .dateTime(LocalDateTime.now())
                .code(ErrorValidation.INVALID_DATABASE_DELETE_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
