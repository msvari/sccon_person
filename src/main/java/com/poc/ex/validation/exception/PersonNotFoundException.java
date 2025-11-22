package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() { super(ErrorValidation.INVALID_PERSON_NOT_FOUND_ERROR.getDescription()); }
}
