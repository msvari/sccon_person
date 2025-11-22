package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class PersonCalculateAgeException extends RuntimeException {

    public PersonCalculateAgeException() { super(ErrorValidation.INVALID_CALCULATE_AGE_ERROR.getDescription()); }

}
