package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException() { super(ErrorValidation.INVALID_PERSON_ALREADY_EXISTS_ERROR.getDescription()); }
}
