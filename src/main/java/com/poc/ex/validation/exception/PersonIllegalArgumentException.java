package com.poc.ex.validation.exception;

import com.poc.ex.model.enumeration.ErrorValidation;

public class PersonIllegalArgumentException extends RuntimeException {

    public PersonIllegalArgumentException(ErrorValidation errorValidation) { super(errorValidation.getDescription()); }

}
